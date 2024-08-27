package org.chainoptim.features.production.controller;

import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.chainoptim.core.scsnapshot.model.SupplyChainSnapshot;
import org.chainoptim.core.scsnapshot.repository.SupplyChainSnapshotRepository;
import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.features.production.dto.*;
import org.chainoptim.features.production.model.Factory;
import org.chainoptim.features.production.repository.FactoryRepository;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.dto.LocationDTOMapper;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FactoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Seed data services
    @Autowired
    private TestDataSeederService seederService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private FactoryRepository factoryRepository;
    @Autowired
    private EntitySanitizerService entitySanitizerService;
    @Autowired
    private SubscriptionPlanLimiterService planLimiterService;
    @Autowired
    private SupplyChainSnapshotRepository snapshotRepository;

    // Necessary seed data
    Integer organizationId;
    String jwtToken;
    CreateLocationDTO locationDTO;
    Location location;
    Integer factoryId;

    @BeforeEach
    void setUp() {
        // Set up an organization, user, and JWT token for passing security checks
        Pair<Integer, String> seedResult = seederService.seedDatabaseWithTenant();
        organizationId = seedResult.getFirst();
        jwtToken = seedResult.getSecond();

        // Set up a unit of measurement for factories
        locationDTO = new CreateLocationDTO();
        locationDTO.setAddress("Test Address");
        locationDTO.setOrganizationId(organizationId);

        location = locationRepository.save(LocationDTOMapper.convertCreateLocationDTOToLocation(locationDTO));

        // Set up supply chain snapshot for plan limiter service
        SupplyChainSnapshot supplyChainSnapshot = new SupplyChainSnapshot();
        supplyChainSnapshot.setOrganizationId(organizationId);
        Snapshot snapshot = new Snapshot();
        snapshot.setFactoriesCount(0);
        supplyChainSnapshot.setSnapshot(snapshot);
        snapshotRepository.save(supplyChainSnapshot);

        // Set up factory for search, update and delete tests
        createTestFactories();
    }

    void createTestFactories() {
        Factory factory1 = createTestFactory("Test Factory 1");
        factoryId = factory1.getId();

        Factory factory2 = createTestFactory("Test Factory 2");

        Factory factory3 = createTestFactory("Test Factory 3");
    }

    Factory createTestFactory(String factoryName) {
        Factory factory = new Factory();
        factory.setName(factoryName);
        factory.setOrganizationId(organizationId);
        factory.setLocation(location);

        return factoryRepository.save(factory);
    }

    @Test
    void testSearchFactories() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/factories/organization/advanced/" + organizationId.toString()
                + "?searchQuery=Test"
                + "&sortOption=name"
                + "&ascending=true"
                + "&page=1"
                + "&itemsPerPage=2";
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
        PaginatedResults<FactoriesSearchDTO> paginatedResults = objectMapper.readValue(
                responseContent, new TypeReference<PaginatedResults<FactoriesSearchDTO>>() {});

        // Assert
        assertNotNull(paginatedResults);
        assertEquals(2, paginatedResults.results.size()); // Ensure pagination works
        assertEquals(3, paginatedResults.totalCount); // Ensure total count works
        assertEquals(factoryId, paginatedResults.results.getFirst().getId()); // Ensure sorting works
    }

    @Test
    void testCreateFactory() throws Exception {
        // Arrange
        CreateFactoryDTO factoryDTO = new CreateFactoryDTO("Test Factory - Unique Title 123456789", organizationId, location.getId(), locationDTO, false);
        String factoryDTOJson = objectMapper.writeValueAsString(factoryDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(post("/api/v1/factories/create")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(factoryDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<Factory> invalidCreatedFactoryOptional = factoryRepository.findByName(factoryDTO.getName());
        if (invalidCreatedFactoryOptional.isPresent()) {
            fail("Failed to prevent creation on invalid JWT token");
        }

        // Act
        mockMvc.perform(post("/api/v1/factories/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(factoryDTOJson));

        // Assert
        Optional<Factory> createdFactoryOptional = factoryRepository.findByName(factoryDTO.getName());
        if (createdFactoryOptional.isEmpty()) {
            fail("Created factory has not been found");
        }
        Factory createdFactory = createdFactoryOptional.get();
        assertNotNull(createdFactory);
        assertEquals(factoryDTO.getName(), createdFactory.getName());
        assertEquals(factoryDTO.getOrganizationId(), createdFactory.getOrganizationId());
        assertEquals(factoryDTO.getLocationId(), createdFactory.getLocation().getId());

    }

    @Test
    void testUpdateFactory() throws Exception {
        // Arrange
        UpdateFactoryDTO factoryDTO = new UpdateFactoryDTO(factoryId, "Test Factory - Updated Unique Title 123456789", location.getId(), null, false);
        String factoryDTOJson = objectMapper.writeValueAsString(factoryDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(put("/api/v1/factories/update")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(factoryDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<Factory> invalidUpdatedFactoryOptional = factoryRepository.findByName(factoryDTO.getName());
        if (invalidUpdatedFactoryOptional.isPresent()) {
            fail("Failed to prevent update on invalid JWT token.");
        }

        // Act
        mockMvc.perform(put("/api/v1/factories/update")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(factoryDTOJson))
                        .andExpect(status().isOk());

        // Assert
        Optional<Factory> updatedFactoryOptional = factoryRepository.findByName(factoryDTO.getName());
        if (updatedFactoryOptional.isEmpty()) {
            fail("Updated factory has not been found");
        }
        Factory updatedFactory = updatedFactoryOptional.get();
        assertNotNull(updatedFactory);
        assertEquals(factoryDTO.getName(), updatedFactory.getName());
        assertEquals(factoryDTO.getLocationId(), updatedFactory.getLocation().getId());
    }

    @Test
    void testDeleteFactory() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/factories/delete/" + factoryId;
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(delete(url)
                        .header("Authorization", "Bearer " + invalidJWTToken))
                        .andExpect(status().is(403));

        // Assert
        Optional<Factory> invalidUpdatedFactoryOptional = factoryRepository.findById(factoryId);
        if (invalidUpdatedFactoryOptional.isEmpty()) {
            fail("Failed to prevent deletion on invalid JWT Token.");
        }

        // Act
        mockMvc.perform(delete(url)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        // Assert
        Optional<Factory> updatedFactoryOptional = factoryRepository.findById(factoryId);
        if (updatedFactoryOptional.isPresent()) {
            fail("Factory has not been deleted as expected.");
        }
    }

}
