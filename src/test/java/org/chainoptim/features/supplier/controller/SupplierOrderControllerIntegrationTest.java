package org.chainoptim.features.supplier.controller;

import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.chainoptim.core.scsnapshot.model.SupplyChainSnapshot;
import org.chainoptim.core.scsnapshot.repository.SupplyChainSnapshotRepository;
import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.chainoptim.features.supplier.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supplier.dto.UpdateSupplierOrderDTO;
import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.features.supplier.service.SupplierOrderService;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.dto.LocationDTOMapper;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.enums.OrderStatus;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.testutil.TestDataSeederService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SupplierOrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SupplierOrderService supplierOrderService;
    @MockBean
    private KafkaTemplate<String, SupplierOrderEvent> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // Seed data services
    @Autowired
    private TestDataSeederService seederService;
    @Autowired
    private SupplierOrderRepository supplierOrderRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private EntitySanitizerService entitySanitizerService;
    @Autowired
    private SubscriptionPlanLimiterService planLimiterService;
    @Autowired
    private SupplyChainSnapshotRepository snapshotRepository;

    // Necessary seed data
    Integer organizationId;
    String jwtToken;
    Integer supplierId;
    List<Integer> supplierOrderIds = new ArrayList<>();
    Component component;

    @BeforeEach
    void setUp() {
        // Set up an organization, user, and JWT token for passing security checks
        Pair<Integer, String> seedResult = seederService.seedDatabaseWithTenant();
        organizationId = seedResult.getFirst();
        jwtToken = seedResult.getSecond();

        createLocation();

        // Set up a supply chain snapshot for plan limit checks
        createSupplyChainSnapshot();

        createTestSupplier();

        createTestComponent();

        // Set up supplier orders for search, update and delete tests
        createTestSupplierOrders();

        // - Mock the KafkaTemplate send method
        CompletableFuture<SendResult<String, SupplierOrderEvent>> completableFuture = CompletableFuture.completedFuture(new SendResult<>(null, null));
        when(kafkaTemplate.send(anyString(), any(SupplierOrderEvent.class))).thenReturn(completableFuture);
    }

    @Test
    void testSearchSupplierOrders() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/supplier-orders/organization/advanced/" + supplierId.toString()
                + "?searchQuery="
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
        PaginatedResults<SupplierOrder> paginatedResults = objectMapper.readValue(
                responseContent, new TypeReference<PaginatedResults<SupplierOrder>>() {});

        // Assert
        assertNotNull(paginatedResults);
        assertEquals(2, paginatedResults.results.size()); // Ensure pagination works
        assertEquals(3, paginatedResults.totalCount); // Ensure total count works
        assertEquals(supplierOrderIds.getFirst(), paginatedResults.results.getFirst().getId()); // Ensure sorting works
    }

    @Test
    void testCreateSupplierOrder() throws Exception {
        // Arrange
        CreateSupplierOrderDTO supplierOrderDTO = getCreateSupplierOrderDTO("O123");

        String supplierOrderDTOJson = objectMapper.writeValueAsString(supplierOrderDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(post("/api/v1/supplier-orders/create")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierOrderDTOJson))
                        .andExpect(status().is(403));

        // Assert
        Optional<SupplierOrder> invalidCreatedSupplierOrderOptional = supplierOrderRepository.findByCompanyId(supplierOrderDTO.getCompanyId());
        if (invalidCreatedSupplierOrderOptional.isPresent()) {
            fail("Failed to prevent creation on invalid JWT token");
        }

        // Act
        mockMvc.perform(post("/api/v1/supplier-orders/create")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(supplierOrderDTOJson))
                .andExpect(status().isOk());

        // Assert
        Optional<SupplierOrder> createdSupplierOrderOptional = supplierOrderRepository.findByCompanyId(supplierOrderDTO.getCompanyId());
        if (createdSupplierOrderOptional.isEmpty()) {
            fail("Created supplier order has not been found");
        }
        SupplierOrder createdSupplierOrder = createdSupplierOrderOptional.get();

        assertNotNull(createdSupplierOrder);
        assertEquals(supplierOrderDTO.getCompanyId(), createdSupplierOrder.getCompanyId());
        assertEquals(supplierOrderDTO.getOrganizationId(), createdSupplierOrder.getOrganizationId());
        assertEquals(supplierOrderDTO.getSupplierId(), createdSupplierOrder.getSupplierId());
        assertEquals(supplierOrderDTO.getQuantity(), createdSupplierOrder.getQuantity());
        assertEquals(supplierOrderDTO.getStatus(), createdSupplierOrder.getStatus());
        assertEquals(supplierOrderDTO.getComponentId(), createdSupplierOrder.getComponent().getId());
    }

    @Test
    void testCreateSupplierOrdersInBulk() throws Exception {
        // Arrange
        List<CreateSupplierOrderDTO> supplierOrderDTOs = List.of(
                getCreateSupplierOrderDTO("O1"),
                getCreateSupplierOrderDTO("O2"),
                getCreateSupplierOrderDTO("O3")
        );
        String supplierOrderDTOJson = objectMapper.writeValueAsString(supplierOrderDTOs);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(post("/api/v1/supplier-orders/create/bulk")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierOrderDTOJson))
                .andExpect(status().is(403));

        // Assert
        Optional<SupplierOrder> invalidCreatedSupplierOrderOptional = supplierOrderRepository.findByCompanyId(supplierOrderDTOs.getFirst().getCompanyId());
        if (invalidCreatedSupplierOrderOptional.isPresent()) {
            fail("Failed to prevent creation on invalid JWT token");
        }

        // Act
        mockMvc.perform(post("/api/v1/supplier-orders/create/bulk")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierOrderDTOJson))
                .andExpect(status().isOk());

        // Assert
        List<SupplierOrder> createdSupplierOrders = supplierOrderRepository.findByCompanyIds(supplierOrderDTOs.stream().map(CreateSupplierOrderDTO::getCompanyId).toList());
        if (createdSupplierOrders.size() != 3) {
            fail("Created supplier order has not been found");
        }
        CreateSupplierOrderDTO supplierOrderDTO = supplierOrderDTOs.getFirst();
        SupplierOrder createdSupplierOrder = createdSupplierOrders.getFirst();

        assertNotNull(createdSupplierOrders);
        assertEquals(3, createdSupplierOrders.size());
        assertEquals(supplierOrderDTO.getCompanyId(), createdSupplierOrder.getCompanyId());
        assertEquals(supplierOrderDTO.getOrganizationId(), createdSupplierOrder.getOrganizationId());
        assertEquals(supplierOrderDTO.getSupplierId(), createdSupplierOrder.getSupplierId());
        assertEquals(supplierOrderDTO.getQuantity(), createdSupplierOrder.getQuantity());
        assertEquals(supplierOrderDTO.getStatus(), createdSupplierOrder.getStatus());
        assertEquals(supplierOrderDTO.getComponentId(), createdSupplierOrder.getComponent().getId());
    }

    @Test
    @Transactional
    void testUpdateSupplierOrdersInBulk() throws Exception {
        // Arrange
        List<SupplierOrder> existingOrders = supplierOrderRepository.findByIds(supplierOrderIds)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier orders not found"));
        List<UpdateSupplierOrderDTO> supplierOrderDTOs = existingOrders.stream()
                .map(this::getUpdateSupplierOrderDTO).toList();

        String supplierOrderDTOJson = objectMapper.writeValueAsString(supplierOrderDTOs);
        String invalidJWTToken = "Invalid";
        List<String> updatedCompanyIds = supplierOrderDTOs.stream().map(UpdateSupplierOrderDTO::getCompanyId).toList();

        // Act (invalid security credentials)
        mockMvc.perform(put("/api/v1/supplier-orders/update/bulk")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierOrderDTOJson))
                .andExpect(status().is(403));

        // Assert
        List<SupplierOrder> invalidUpdatedSupplierOrders = supplierOrderRepository.findByCompanyIds(updatedCompanyIds);
        if (!invalidUpdatedSupplierOrders.isEmpty()) {
            fail("Failed to prevent update on invalid JWT token");
        }

        // Act
        mockMvc.perform(put("/api/v1/supplier-orders/update/bulk")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(supplierOrderDTOJson))
                .andExpect(status().isOk()).andReturn();

        // Assert
        List<SupplierOrder> updatedSupplierOrders = supplierOrderRepository.findByCompanyIds(updatedCompanyIds);

        if (updatedSupplierOrders.size() != 3) {
            fail("Updated supplier orders have not been found");
        }
        UpdateSupplierOrderDTO supplierOrderDTO = supplierOrderDTOs.getFirst();
        SupplierOrder createdSupplierOrder = updatedSupplierOrders.getFirst();

        assertNotNull(updatedSupplierOrders);
        assertEquals(3, updatedSupplierOrders.size());
        assertEquals(supplierOrderDTO.getCompanyId(), createdSupplierOrder.getCompanyId());
        assertEquals(supplierOrderDTO.getOrganizationId(), createdSupplierOrder.getOrganizationId());
        assertEquals(supplierOrderDTO.getQuantity(), createdSupplierOrder.getQuantity());
        assertEquals(supplierOrderDTO.getStatus(), createdSupplierOrder.getStatus());
        assertEquals(supplierOrderDTO.getComponentId(), createdSupplierOrder.getComponent().getId());
    }

    @Test
    void testDeleteSupplierOrdersInBulk() throws Exception {
        // Arrange
        String orderIdsJson = objectMapper.writeValueAsString(supplierOrderIds);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(delete("/api/v1/supplier-orders/delete/bulk")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderIdsJson))
                .andExpect(status().is(403));

        // Assert
        List<SupplierOrder> invalidDeletedSupplierOrders = supplierOrderRepository.findByIds(supplierOrderIds)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier orders not found"));
        if (invalidDeletedSupplierOrders.size() != 3) {
            fail("Failed to prevent deletion on invalid JWT token");
        }

        // Act
        mockMvc.perform(delete("/api/v1/supplier-orders/delete/bulk")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderIdsJson))
                .andExpect(status().isOk());

        // Assert
        Optional<List<SupplierOrder>> deletedSupplierOrders = supplierOrderRepository.findByIds(supplierOrderIds);
        if (deletedSupplierOrders.isPresent() && !deletedSupplierOrders.get().isEmpty()) {
            fail("Deleted supplier orders have been found");
        }
    }

    private CreateSupplierOrderDTO getCreateSupplierOrderDTO(String companyId) {
        CreateSupplierOrderDTO supplierOrderDTO = new CreateSupplierOrderDTO();
        supplierOrderDTO.setOrganizationId(organizationId);
        supplierOrderDTO.setSupplierId(supplierId);
        supplierOrderDTO.setCompanyId(companyId);
        supplierOrderDTO.setComponentId(component.getId());
        supplierOrderDTO.setQuantity(10f);
        supplierOrderDTO.setStatus(OrderStatus.PLACED);
        return supplierOrderDTO;
    }

    private UpdateSupplierOrderDTO getUpdateSupplierOrderDTO(SupplierOrder order) {
        UpdateSupplierOrderDTO updateSupplierOrderDTO = new UpdateSupplierOrderDTO();
        updateSupplierOrderDTO.setId(order.getId());
        updateSupplierOrderDTO.setCompanyId(order.getCompanyId() + " Updated");
        updateSupplierOrderDTO.setOrganizationId(order.getOrganizationId());
        updateSupplierOrderDTO.setComponentId(order.getComponent().getId());
        updateSupplierOrderDTO.setQuantity(order.getQuantity() != null ? order.getQuantity() + 10f : null);
        updateSupplierOrderDTO.setStatus(OrderStatus.DELIVERED);

        return updateSupplierOrderDTO;
    }

    // Testing
    void createLocation() {
        CreateLocationDTO locationDTO = new CreateLocationDTO();
        locationDTO.setAddress("Test Address");
        locationDTO.setOrganizationId(organizationId);

        locationRepository.save(LocationDTOMapper.convertCreateLocationDTOToLocation(locationDTO));
    }

    void createSupplyChainSnapshot() {
        SupplyChainSnapshot supplyChainSnapshot = new SupplyChainSnapshot();
        supplyChainSnapshot.setOrganizationId(organizationId);
        Snapshot snapshot = new Snapshot();
        snapshot.setSupplierOrdersCount(0);
        supplyChainSnapshot.setSnapshot(snapshot);
        snapshotRepository.save(supplyChainSnapshot);
    }

    void createTestSupplier() {
        Supplier supplier = new Supplier();
        supplier.setOrganizationId(organizationId);
        supplier.setName("Test Supplier");

        supplierId = supplierRepository.save(supplier).getId();
    }

    void createTestComponent() {
        Component newComponent = new Component();
        newComponent.setOrganizationId(organizationId);
        newComponent.setName("Test Component");

        component = componentRepository.save(newComponent);
    }

    void createTestSupplierOrders() {
        SupplierOrder supplierOrder1 = createTestSupplierOrder("O01");
        supplierOrderIds.add(supplierOrder1.getId());

        SupplierOrder supplierOrder2 = createTestSupplierOrder("O02");
        supplierOrderIds.add(supplierOrder2.getId());

        SupplierOrder supplierOrder3 = createTestSupplierOrder("O03");
        supplierOrderIds.add(supplierOrder3.getId());
    }

    SupplierOrder createTestSupplierOrder(String companyId) {
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setCompanyId(companyId);
        supplierOrder.setOrganizationId(organizationId);
        supplierOrder.setSupplierId(supplierId);
        supplierOrder.setComponent(component);
        supplierOrder.setStatus(OrderStatus.PLACED);

        return supplierOrderRepository.save(supplierOrder);
    }
}
