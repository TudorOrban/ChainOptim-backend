package org.chainoptim.features.supply.repository;

import org.chainoptim.core.tenant.organization.model.Organization;
import org.chainoptim.core.tenant.organization.model.SubscriptionPlanTier;
import org.chainoptim.features.goods.component.model.Component;
import org.chainoptim.features.supply.supplier.model.Supplier;
import org.chainoptim.features.supply.supplierorder.model.SupplierOrder;
import org.chainoptim.features.supply.supplierorder.repository.SupplierOrderRepository;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class SupplierOrdersRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SupplierOrderRepository supplierOrderRepository;

    Integer organizationId;
    Integer locationId;
    Integer supplierId;
    Integer supplierOrderId;
    Component component;

    @BeforeEach
    void setUp() {
        // Set up an organization and a unit of measurement
        Organization organization = Organization.builder()
                .name("Test Org")
                .subscriptionPlanTier(SubscriptionPlanTier.PROFESSIONAL)
                .build();

        entityManager.persist(organization);
        entityManager.flush();
        organizationId = organization.getId();

        Location location = new Location();
        location.setAddress("Test Address");
        location.setOrganizationId(organizationId);

        entityManager.persist(location);
        entityManager.flush();
        locationId = location.getId();

        // Set up supplier for update and delete tests
        Supplier supplier = addTestSupplier();
        supplierId = supplier.getId();

        Component newComponent = new Component();
        newComponent.setName("Test Component");
        newComponent.setOrganizationId(organizationId);

        component = entityManager.persist(newComponent);
        entityManager.flush();

        SupplierOrder supplierOrder = addTestSupplierOrder();
        supplierOrderId = supplierOrder.getId();
    }

    @Test
    void testCreateSupplierOrder() {
        // Arrange
        SupplierOrder supplierOrder = addTestSupplierOrder();

        // Act
        SupplierOrder savedSupplierOrder = entityManager.persist(supplierOrder);
        entityManager.flush();

        // Assert
        Optional<SupplierOrder> foundSupplierOrderOpt = supplierOrderRepository.findById(savedSupplierOrder.getId());
        assertTrue(foundSupplierOrderOpt.isPresent(), "Supplier should be found in the database");

        SupplierOrder foundSupplierOrder = foundSupplierOrderOpt.get();
        assertEquals(savedSupplierOrder.getOrganizationId(), foundSupplierOrder.getOrganizationId());
        assertEquals(savedSupplierOrder.getSupplierId(), foundSupplierOrder.getSupplierId());
        assertEquals(savedSupplierOrder.getOrderDate(), foundSupplierOrder.getOrderDate());
        assertEquals(savedSupplierOrder.getQuantity(), foundSupplierOrder.getQuantity());
    }

    @Test
    void testUpdateSupplier() {
        // Arrange
        Optional<SupplierOrder> supplierOrderOptional = supplierOrderRepository.findById(supplierOrderId); // Id from setUp
        if (supplierOrderOptional.isEmpty()) {
            fail("Expected an existing supplier order with id " + supplierOrderId);
        }

        SupplierOrder supplierOrder = supplierOrderOptional.get();
        supplierOrder.setStatus(OrderStatus.DELIVERED);
        supplierOrder.setQuantity(20f);
        LocalDateTime deliveryDate = LocalDateTime.parse("2021-01-02T00:00:00");
        supplierOrder.setDeliveryDate(deliveryDate);

        // Act
        SupplierOrder updatedSupplierOrder = supplierOrderRepository.save(supplierOrder);

        // Assert
        assertNotNull(updatedSupplierOrder);
        assertEquals(supplierOrder.getOrganizationId(), updatedSupplierOrder.getOrganizationId());
        assertEquals(supplierOrder.getSupplierId(), updatedSupplierOrder.getSupplierId());
        assertEquals(supplierOrder.getOrderDate(), updatedSupplierOrder.getOrderDate());
        assertEquals(supplierOrder.getQuantity(), updatedSupplierOrder.getQuantity());
        assertEquals(supplierOrder.getStatus(), updatedSupplierOrder.getStatus());
        assertEquals(supplierOrder.getDeliveryDate(), updatedSupplierOrder.getDeliveryDate());
    }

    @Test
    void testDeleteSupplier() {
        // Arrange
        Optional<SupplierOrder> supplierOrderToBeDeletedOptional = supplierOrderRepository.findById(supplierOrderId);
        if (supplierOrderToBeDeletedOptional.isEmpty()) {
            fail("Expected an existing supplier order with id " + supplierOrderId);
        }

        SupplierOrder supplierOrderToBeDeleted = supplierOrderToBeDeletedOptional.get();

        // Act
        supplierOrderRepository.delete(supplierOrderToBeDeleted);

        // Assert
        Optional<SupplierOrder> deletedSupplierOrderOptional = supplierOrderRepository.findById(supplierOrderId);
        if (deletedSupplierOrderOptional.isPresent()) {
            fail("Expected supplier order with id 1 to have been deleted");
        }
    }

    Supplier addTestSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("Test Supplier");
        supplier.setOrganizationId(organizationId);
        Location location = new Location();
        location.setId(locationId);
        supplier.setLocation(location);

        return entityManager.persist(supplier);
    }

    SupplierOrder addTestSupplierOrder() {
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setSupplierId(supplierId);
        supplierOrder.setOrganizationId(organizationId);
        LocalDateTime orderDate = LocalDateTime.parse("2021-01-01T00:00:00");
        supplierOrder.setOrderDate(orderDate);
        supplierOrder.setStatus(OrderStatus.PLACED);
        supplierOrder.setQuantity(10f);
        supplierOrder.setComponent(component);

        return entityManager.persist(supplierOrder);
    }
}
