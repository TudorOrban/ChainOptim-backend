package org.chainoptim.features.storage.controller;

import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.chainoptim.core.scsnapshot.model.SupplyChainSnapshot;
import org.chainoptim.core.scsnapshot.repository.SupplyChainSnapshotRepository;
import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.features.storage.dto.CreateWarehouseDTO;
import org.chainoptim.features.storage.dto.WarehousesSearchDTO;
import org.chainoptim.features.storage.dto.UpdateWarehouseDTO;
import org.chainoptim.features.storage.model.Warehouse;
import org.chainoptim.features.storage.repository.WarehouseRepository;
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
class WarehouseControllerIntegrationTest {

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
    private WarehouseRepository warehouseRepository;
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
    Integer warehouseId;

    @BeforeEach
    void setUp() {
        // Set up an organization, user, and JWT token for passing security checks
        Pair<Integer, String> seedResult = seederService.seedDatabaseWithTenant();
        organizationId = seedResult.getFirst();
        jwtToken = seedResult.getSecond();

        // Set up a unit of measurement for warehouses
        locationDTO = new CreateLocationDTO();
        locationDTO.setAddress("Test Address");
        locationDTO.setOrganizationId(organizationId);

        location = locationRepository.save(LocationDTOMapper.convertCreateLocationDTOToLocation(locationDTO));

        // Set up supply chain snapshot for plan limiter service
        SupplyChainSnapshot supplyChainSnapshot = new SupplyChainSnapshot();
        supplyChainSnapshot.setOrganizationId(organizationId);
        Snapshot snapshot = new Snapshot();
        snapshot.setWarehousesCount(0);
        supplyChainSnapshot.setSnapshot(snapshot);
        snapshotRepository.save(supplyChainSnapshot);

        // Set up warehouse for search, update and delete tests
        createTestWarehouses();
    }

    void createTestWarehouses() {
        Warehouse warehouse1 = createTestWarehouse("Test Warehouse 1");
        warehouseId = warehouse1.getId();

        Warehouse warehouse2 = createTestWarehouse("Test Warehouse 2");

        Warehouse warehouse3 = createTestWarehouse("Test Warehouse 3");
    }

    Warehouse createTestWarehouse(String warehouseName) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(warehouseName);
        warehouse.setOrganizationId(organizationId);
        warehouse.setLocation(location);

        return warehouseRepository.save(warehouse);
    }

    @Test
    void testSearchWarehouses() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/warehouses/organization/advanced/" + organizationId.toString()
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
        PaginatedResults<WarehousesSearchDTO> paginatedResults = objectMapper.readValue(
                responseContent, new TypeReference<PaginatedResults<WarehousesSearchDTO>>() {});

        // Assert
        assertNotNull(paginatedResults);
        assertEquals(2, paginatedResults.results.size()); // Ensure pagination works
        assertEquals(3, paginatedResults.totalCount); // Ensure total count works
        assertEquals(warehouseId, paginatedResults.results.getFirst().getId()); // Ensure sorting works
    }

    @Test
    void testCreateWarehouse() throws Exception {
        // Arrange
        CreateWarehouseDTO warehouseDTO = new CreateWarehouseDTO("Test Warehouse - Unique Title 123456789", organizationId, location.getId(), locationDTO, false);
        String warehouseDTOJson = objectMapper.writeValueAsString(warehouseDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(post("/api/v1/warehouses/create")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(warehouseDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<Warehouse> invalidCreatedWarehouseOptional = warehouseRepository.findByName(warehouseDTO.getName());
        if (invalidCreatedWarehouseOptional.isPresent()) {
            fail("Failed to prevent creation on invalid JWT token");
        }

        // Act
        mockMvc.perform(post("/api/v1/warehouses/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(warehouseDTOJson));

        // Assert
        Optional<Warehouse> createdWarehouseOptional = warehouseRepository.findByName(warehouseDTO.getName());
        if (createdWarehouseOptional.isEmpty()) {
            fail("Created warehouse has not been found");
        }
        Warehouse createdWarehouse = createdWarehouseOptional.get();
        assertNotNull(createdWarehouse);
        assertEquals(warehouseDTO.getName(), createdWarehouse.getName());
        assertEquals(warehouseDTO.getOrganizationId(), createdWarehouse.getOrganizationId());
        assertEquals(warehouseDTO.getLocationId(), createdWarehouse.getLocation().getId());

    }

    @Test
    void testUpdateWarehouse() throws Exception {
        // Arrange
        UpdateWarehouseDTO warehouseDTO = new UpdateWarehouseDTO(warehouseId, "Test Warehouse - Updated Unique Title 123456789", location.getId(), null, false);
        String warehouseDTOJson = objectMapper.writeValueAsString(warehouseDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(put("/api/v1/warehouses/update")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(warehouseDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<Warehouse> invalidUpdatedWarehouseOptional = warehouseRepository.findByName(warehouseDTO.getName());
        if (invalidUpdatedWarehouseOptional.isPresent()) {
            fail("Failed to prevent update on invalid JWT token.");
        }

        // Act
        mockMvc.perform(put("/api/v1/warehouses/update")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(warehouseDTOJson))
                        .andExpect(status().isOk());

        // Assert
        Optional<Warehouse> updatedWarehouseOptional = warehouseRepository.findByName(warehouseDTO.getName());
        if (updatedWarehouseOptional.isEmpty()) {
            fail("Updated warehouse has not been found");
        }
        Warehouse updatedWarehouse = updatedWarehouseOptional.get();
        assertNotNull(updatedWarehouse);
        assertEquals(warehouseDTO.getName(), updatedWarehouse.getName());
        assertEquals(warehouseDTO.getLocationId(), updatedWarehouse.getLocation().getId());
    }

    @Test
    void testDeleteWarehouse() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/warehouses/delete/" + warehouseId;
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(delete(url)
                        .header("Authorization", "Bearer " + invalidJWTToken))
                        .andExpect(status().is(403));

        // Assert
        Optional<Warehouse> invalidUpdatedWarehouseOptional = warehouseRepository.findById(warehouseId);
        if (invalidUpdatedWarehouseOptional.isEmpty()) {
            fail("Failed to prevent deletion on invalid JWT Token.");
        }

        // Act
        mockMvc.perform(delete(url)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        // Assert
        Optional<Warehouse> updatedWarehouseOptional = warehouseRepository.findById(warehouseId);
        if (updatedWarehouseOptional.isPresent()) {
            fail("Warehouse has not been deleted as expected.");
        }
    }

}
