package org.chainoptim.features.productpipeline.controller;

import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.chainoptim.core.scsnapshot.model.SupplyChainSnapshot;
import org.chainoptim.core.scsnapshot.repository.SupplyChainSnapshotRepository;
import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.features.goods.controller.CreateUnitOfMeasurementDTO;
import org.chainoptim.features.goods.controller.UnitDTOMapper;
import org.chainoptim.features.goods.unit.model.NewUnitOfMeasurement;
import org.chainoptim.features.goods.controller.UnitOfMeasurement;
import org.chainoptim.features.goods.controller.UnitOfMeasurementRepository;
import org.chainoptim.features.goods.component.dto.ComponentsSearchDTO;
import org.chainoptim.features.goods.component.dto.CreateComponentDTO;
import org.chainoptim.features.goods.controller.UpdateComponentDTO;
import org.chainoptim.features.goods.component.model.Component;
import org.chainoptim.features.goods.component.repository.ComponentRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.testutil.TestDataSeederService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ComponentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Seed data services
    @Autowired
    private TestDataSeederService seederService;
    @Autowired
    private UnitOfMeasurementRepository unitOfMeasurementRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private EntitySanitizerService entitySanitizerService;
    @Autowired
    private SubscriptionPlanLimiterService planLimiterService;
    @Autowired
    private SupplyChainSnapshotRepository snapshotRepository;

    // Necessary seed data
    Integer organizationId;
    String jwtToken;
    CreateUnitOfMeasurementDTO unitDTO;
    UnitOfMeasurement unit;
    Integer componentId;

    @BeforeEach
    void setUp() {
        // Set up an organization, user, and JWT token for passing security checks
        Pair<Integer, String> seedResult = seederService.seedDatabaseWithTenant();
        organizationId = seedResult.getFirst();
        jwtToken = seedResult.getSecond();

        // Set up a unit of measurement for components
        unitDTO = new CreateUnitOfMeasurementDTO();
        unitDTO.setName("Test unit");
        unitDTO.setUnitType("Test unit type");
        unitDTO.setOrganizationId(organizationId);

        unit = unitOfMeasurementRepository.save(UnitDTOMapper.convertCreateUnitDTOToUnit(unitDTO));

        // Set up supply chain snapshot for plan limiter service
        SupplyChainSnapshot supplyChainSnapshot = new SupplyChainSnapshot();
        supplyChainSnapshot.setOrganizationId(organizationId);
        Snapshot snapshot = new Snapshot();
        snapshot.setComponentsCount(0);
        supplyChainSnapshot.setSnapshot(snapshot);
        snapshotRepository.save(supplyChainSnapshot);

        // Set up component for search, update and delete tests
        createTestComponents();
    }

    void createTestComponents() {
        Component component1 = new Component();
        component1.setName("Test Component 1");
        component1.setDescription("Test Description 1");
        component1.setOrganizationId(organizationId);
        component1.setUnit(unit);

        component1 = componentRepository.save(component1);
        componentId = component1.getId();

        Component component2 = new Component();
        component2.setName("Test Component 2");
        component2.setDescription("Test Description 2");
        component2.setOrganizationId(organizationId);
        component2.setUnit(unit);

        componentRepository.save(component2);

        Component component3 = new Component();
        component3.setName("Test Component 3");
        component3.setDescription("Test Description 3");
        component3.setOrganizationId(organizationId);
        component3.setUnit(unit);

        componentRepository.save(component3);
    }

    @Test
    void testSearchComponents() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/components/organization/" + organizationId.toString() + "/small";
        String invalidJWTToken = "Invalid";

        // Act and assert error status for invalid credentials
        MvcResult invalidMvcResult = mockMvc.perform(get(url)
                .header("Authorization", "Bearer " + invalidJWTToken))
                .andExpect(status().is(403))
                .andReturn();

        // Act
        MvcResult mvcResult = mockMvc.perform(get(url)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        // Extract and deserialize response
        String responseContent = mvcResult.getResponse().getContentAsString();
        List<ComponentsSearchDTO> results = objectMapper.readValue(
                responseContent, new TypeReference<List<ComponentsSearchDTO>>() {});

        // Assert
        assertNotNull(results);
        assertEquals(3, results.size());
    }

    @Test
    void testCreateComponent() throws Exception {
        // Arrange
        CreateComponentDTO componentDTO = new CreateComponentDTO("Test Component - Unique Title 123456789", "Test Description", organizationId, unit.getId(), unitDTO, false, new NewUnitOfMeasurement());
        String componentDTOJson = objectMapper.writeValueAsString(componentDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(post("/api/v1/components/create")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(componentDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<Component> invalidCreatedComponentOptional = componentRepository.findByName(componentDTO.getName());
        if (invalidCreatedComponentOptional.isPresent()) {
            fail("Failed to prevent creation on invalid JWT token");
        }

        // Act
        mockMvc.perform(post("/api/v1/components/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(componentDTOJson));

        // Assert
        Optional<Component> createdComponentOptional = componentRepository.findByName(componentDTO.getName());
        if (createdComponentOptional.isEmpty()) {
            fail("Created component has not been found");
        }
        Component createdComponent = createdComponentOptional.get();
        assertNotNull(createdComponent);
        assertEquals(componentDTO.getName(), createdComponent.getName());
        assertEquals(componentDTO.getDescription(), createdComponent.getDescription());
        assertEquals(componentDTO.getOrganizationId(), createdComponent.getOrganizationId());
        assertEquals(componentDTO.getUnitId(), createdComponent.getUnit().getId());

    }

    @Test
    void testUpdateComponent() throws Exception {
        // Arrange
        UpdateComponentDTO componentDTO = new UpdateComponentDTO(componentId, "Test Component - Updated Unique Title 123456789", "Test Description", unit.getId(), new NewUnitOfMeasurement());
        String componentDTOJson = objectMapper.writeValueAsString(componentDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(put("/api/v1/components/update")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(componentDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<Component> invalidUpdatedComponentOptional = componentRepository.findByName(componentDTO.getName());
        if (invalidUpdatedComponentOptional.isPresent()) {
            fail("Failed to prevent update on invalid JWT token.");
        }

        // Act
        mockMvc.perform(put("/api/v1/components/update")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(componentDTOJson))
                        .andExpect(status().isOk());

        // Assert
        Optional<Component> updatedComponentOptional = componentRepository.findByName(componentDTO.getName());
        if (updatedComponentOptional.isEmpty()) {
            fail("Updated component has not been found");
        }
        Component updatedComponent = updatedComponentOptional.get();
        assertNotNull(updatedComponent);
        assertEquals(componentDTO.getName(), updatedComponent.getName());
        assertEquals(componentDTO.getDescription(), updatedComponent.getDescription());
        assertEquals(componentDTO.getUnitId(), updatedComponent.getUnit().getId());
    }

    @Test
    void testDeleteComponent() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/components/delete/" + componentId;
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(delete(url)
                        .header("Authorization", "Bearer " + invalidJWTToken))
                        .andExpect(status().is(403));

        // Assert
        Optional<Component> invalidUpdatedComponentOptional = componentRepository.findById(componentId);
        if (invalidUpdatedComponentOptional.isEmpty()) {
            fail("Failed to prevent deletion on invalid JWT Token.");
        }

        // Act
        mockMvc.perform(delete(url)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        // Assert
        Optional<Component> updatedComponentOptional = componentRepository.findById(componentId);
        if (updatedComponentOptional.isPresent()) {
            fail("Component has not been deleted as expected.");
        }
    }

}
