package org.chainoptim.features.productpipeline.controller;

import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.chainoptim.core.scsnapshot.model.SupplyChainSnapshot;
import org.chainoptim.core.scsnapshot.repository.SupplyChainSnapshotRepository;
import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.features.productpipeline.dto.StagesSearchDTO;
import org.chainoptim.features.productpipeline.dto.CreateStageDTO;
import org.chainoptim.features.productpipeline.dto.UpdateStageDTO;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.repository.StageRepository;
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
class StageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Seed data services
    @Autowired
    private TestDataSeederService seederService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StageRepository stageRepository;
    @Autowired
    private EntitySanitizerService entitySanitizerService;
    @Autowired
    private SubscriptionPlanLimiterService planLimiterService;
    @Autowired
    private SupplyChainSnapshotRepository snapshotRepository;

    // Necessary seed data
    Integer organizationId;
    String jwtToken;
    Integer stageId;
    Integer productId;

    @BeforeEach
    void setUp() {
        // Set up an organization, user, and JWT token for passing security checks
        Pair<Integer, String> seedResult = seederService.seedDatabaseWithTenant();
        organizationId = seedResult.getFirst();
        jwtToken = seedResult.getSecond();

        Product product = new Product();
        product.setOrganizationId(organizationId);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product = productRepository.save(product);
        productId = product.getId();

        // Set up supply chain snapshot for plan limiter service
        SupplyChainSnapshot supplyChainSnapshot = new SupplyChainSnapshot();
        supplyChainSnapshot.setOrganizationId(organizationId);
        Snapshot snapshot = new Snapshot();
        snapshot.setProductStagesCount(0);
        supplyChainSnapshot.setSnapshot(snapshot);
        snapshotRepository.save(supplyChainSnapshot);

        // Set up stage for search, update and delete tests
        createTestStages();
    }

    void createTestStages() {
        Stage stage1 = new Stage();
        stage1.setName("Test Stage 1");
        stage1.setDescription("Test Description 1");
        stage1.setOrganizationId(organizationId);
        stage1.setProductId(productId);

        stage1 = stageRepository.save(stage1);
        stageId = stage1.getId();

        Stage stage2 = new Stage();
        stage2.setName("Test Stage 2");
        stage2.setDescription("Test Description 2");
        stage2.setOrganizationId(organizationId);
        stage2.setProductId(productId);

        stageRepository.save(stage2);

        Stage stage3 = new Stage();
        stage3.setName("Test Stage 3");
        stage3.setDescription("Test Description 3");
        stage3.setOrganizationId(organizationId);
        stage3.setProductId(productId);

        stageRepository.save(stage3);
    }

    @Test
    void testSearchStages() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/stages/organization/" + organizationId.toString() + "/small";
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
        List<StagesSearchDTO> results = objectMapper.readValue(
                responseContent, new TypeReference<List<StagesSearchDTO>>() {});

        // Assert
        assertNotNull(results);
        assertEquals(3, results.size());
    }

    @Test
    void testCreateStage() throws Exception {
        // Arrange
        CreateStageDTO stageDTO = new CreateStageDTO(organizationId, productId, "Test Stage - Unique Title 123456789", "Test Description");
        String stageDTOJson = objectMapper.writeValueAsString(stageDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(post("/api/v1/stages/create")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stageDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<Stage> invalidCreatedStageOptional = stageRepository.findByName(stageDTO.getName());
        if (invalidCreatedStageOptional.isPresent()) {
            fail("Failed to prevent creation on invalid JWT token");
        }

        // Act
        mockMvc.perform(post("/api/v1/stages/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stageDTOJson));

        // Assert
        Optional<Stage> createdStageOptional = stageRepository.findByName(stageDTO.getName());
        if (createdStageOptional.isEmpty()) {
            fail("Created stage has not been found");
        }
        Stage createdStage = createdStageOptional.get();
        assertNotNull(createdStage);
        assertEquals(stageDTO.getName(), createdStage.getName());
        assertEquals(stageDTO.getDescription(), createdStage.getDescription());
        assertEquals(stageDTO.getOrganizationId(), createdStage.getOrganizationId());
        assertEquals(stageDTO.getProductId(), createdStage.getProductId());

    }

    @Test
    void testUpdateStage() throws Exception {
        // Arrange
        UpdateStageDTO stageDTO = new UpdateStageDTO(stageId, "Test Stage - Updated Unique Title 123456789", "Test Description");
        String stageDTOJson = objectMapper.writeValueAsString(stageDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(put("/api/v1/stages/update")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stageDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<Stage> invalidUpdatedStageOptional = stageRepository.findByName(stageDTO.getName());
        if (invalidUpdatedStageOptional.isPresent()) {
            fail("Failed to prevent update on invalid JWT token.");
        }

        // Act
        mockMvc.perform(put("/api/v1/stages/update")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stageDTOJson))
                        .andExpect(status().isOk());

        // Assert
        Optional<Stage> updatedStageOptional = stageRepository.findByName(stageDTO.getName());
        if (updatedStageOptional.isEmpty()) {
            fail("Updated stage has not been found");
        }
        Stage updatedStage = updatedStageOptional.get();
        assertNotNull(updatedStage);
        assertEquals(stageDTO.getName(), updatedStage.getName());
        assertEquals(stageDTO.getDescription(), updatedStage.getDescription());
    }

    @Test
    void testDeleteStage() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/stages/delete/" + stageId;
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(delete(url)
                        .header("Authorization", "Bearer " + invalidJWTToken))
                        .andExpect(status().is(403));

        // Assert
        Optional<Stage> invalidUpdatedStageOptional = stageRepository.findById(stageId);
        if (invalidUpdatedStageOptional.isEmpty()) {
            fail("Failed to prevent deletion on invalid JWT Token.");
        }

        // Act
        mockMvc.perform(delete(url)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        // Assert
        Optional<Stage> updatedStageOptional = stageRepository.findById(stageId);
        if (updatedStageOptional.isPresent()) {
            fail("Stage has not been deleted as expected.");
        }
    }

}
