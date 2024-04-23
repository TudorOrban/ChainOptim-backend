package org.chainoptim.features.supplier.controller;

import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.chainoptim.core.scsnapshot.model.SupplyChainSnapshot;
import org.chainoptim.core.scsnapshot.repository.SupplyChainSnapshotRepository;
import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.chainoptim.features.supplier.dto.CreateSupplierDTO;
import org.chainoptim.features.supplier.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.repository.SupplierOrderRepository;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.dto.LocationDTOMapper;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.enums.OrderStatus;
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
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SupplierOrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

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
    Integer supplierOrderId;
    Component component;

    @BeforeEach
    void setUp() {
        // Set up an organization, user, and JWT token for passing security checks
        Pair<Integer, String> seedResult = seederService.seedDatabaseWithTenant();
        organizationId = seedResult.getFirst();
        jwtToken = seedResult.getSecond();

        // Set up a unit of measurement for suppliers
        CreateLocationDTO locationDTO = new CreateLocationDTO();
        locationDTO.setAddress("Test Address");
        locationDTO.setOrganizationId(organizationId);

        locationRepository.save(LocationDTOMapper.convertCreateLocationDTOToLocation(locationDTO));

        // Set up supply chain snapshot for plan limiter service
        SupplyChainSnapshot supplyChainSnapshot = new SupplyChainSnapshot();
        supplyChainSnapshot.setOrganizationId(organizationId);
        Snapshot snapshot = new Snapshot();
        snapshot.setSuppliersCount(0);
        supplyChainSnapshot.setSnapshot(snapshot);
        snapshotRepository.save(supplyChainSnapshot);

        createTestSupplier();

        createTestComponent();

        // Set up supplier orders for search, update and delete tests
        createTestSupplierOrders();
    }

    void createTestSupplier() {
        Supplier supplier = new Supplier();
        supplier.setOrganizationId(organizationId);
        supplier.setName("Test Supplier");

        supplierId = supplierRepository.save(supplier).getId();
    }

    void createTestComponent() {
        Component component = new Component();
        component.setOrganizationId(organizationId);
        component.setName("Test Component");

        component = componentRepository.save(component);
    }

    void createTestSupplierOrders() {
        SupplierOrder supplierOrder1 = createTestSupplierOrder("O1");
        supplierOrderId = supplierOrder1.getId();

        SupplierOrder supplierOrder2 = createTestSupplierOrder("O2");

        SupplierOrder supplierOrder3 = createTestSupplierOrder("O3");
    }

    SupplierOrder createTestSupplierOrder(String companyId) {
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setCompanyId(companyId);
        supplierOrder.setOrganizationId(organizationId);
        supplierOrder.setSupplierId(supplierId);
        supplierOrder.setComponent(component);
        supplierOrder.setStatus(OrderStatus.DELIVERED);

        return supplierOrderRepository.save(supplierOrder);
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
        assertEquals(supplierOrderId, paginatedResults.results.getFirst().getId()); // Ensure sorting works
    }

    @Test
    void testCreateSupplierOrder() throws Exception {
        // Arrange
        CreateSupplierOrderDTO supplierOrderDTO = new CreateSupplierOrderDTO();
        supplierOrderDTO.setOrganizationId(organizationId);
        supplierOrderDTO.setSupplierId(supplierId);
        supplierOrderDTO.setCompanyId("O123");
        supplierOrderDTO.setComponentId(component.getId());
        supplierOrderDTO.setQuantity(10f);
        supplierOrderDTO.setStatus(OrderStatus.PLACED);

        String supplierOrderDTOJson = objectMapper.writeValueAsString(supplierOrderDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(post("/api/v1/suppliers/create")
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
        mockMvc.perform(post("/api/v1/suppliers/create")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(supplierOrderDTOJson));

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
}
