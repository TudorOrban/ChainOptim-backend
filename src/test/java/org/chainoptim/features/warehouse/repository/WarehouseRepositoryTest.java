package org.chainoptim.features.warehouse.repository;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.shared.commonfeatures.location.model.Location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class WarehouseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WarehouseRepository warehouseRepository;

    Integer organizationId;
    Integer locationId;
    Integer warehouseId;

    @BeforeEach
    void setUp() {
        // Set up an organization and a unit of measurement
        Organization organization = Organization.builder()
                .name("Test Org")
                .subscriptionPlanTier(Organization.SubscriptionPlanTier.PRO)
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

        // Set up warehouse for update and delete tests
        Warehouse warehouse = addTestWarehouse();
        warehouseId = warehouse.getId();
    }

    @Test
    void testCreateWarehouse() {
        // Arrange
        Warehouse savedWarehouse = addTestWarehouse();

        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<Warehouse> foundWarehouseOpt = warehouseRepository.findById(savedWarehouse.getId());
        assertTrue(foundWarehouseOpt.isPresent(), "Warehouse should be found in the database");

        Warehouse foundWarehouse = foundWarehouseOpt.get();
        assertEquals(savedWarehouse.getName(), foundWarehouse.getName());
        assertEquals(savedWarehouse.getOrganizationId(), foundWarehouse.getOrganizationId());
        assertEquals(savedWarehouse.getLocation().getId(), foundWarehouse.getLocation().getId());
    }

    @Test
    void testUpdateWarehouse() {
        // Arrange
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId); // Id from setUp
        if (warehouseOptional.isEmpty()) {
            fail("Expected an existing warehouse with id " + warehouseOptional);
        }

        Warehouse warehouse = warehouseOptional.get();
        warehouse.setName("New Test Name");

        // Act
        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);

        // Assert
        assertNotNull(updatedWarehouse);
        assertEquals("New Test Name", updatedWarehouse.getName());
    }

    @Test
    void testDeleteWarehouse() {
        // Arrange
        Optional<Warehouse> warehouseToBeDeletedOptional = warehouseRepository.findById(warehouseId);
        if (warehouseToBeDeletedOptional.isEmpty()) {
            fail("Expected an existing warehouse with id " + warehouseId);
        }

        Warehouse warehouseToBeDeleted = warehouseToBeDeletedOptional.get();

        // Act
        warehouseRepository.delete(warehouseToBeDeleted);

        // Assert
        Optional<Warehouse> deletedWarehouseOptional = warehouseRepository.findById(warehouseId);
        if (deletedWarehouseOptional.isPresent()) {
            fail("Expected warehouse with id 1 to have been deleted");
        }
    }

    Warehouse addTestWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setName("Test Warehouse");
        warehouse.setOrganizationId(organizationId);
        Location location = new Location();
        location.setId(locationId);
        warehouse.setLocation(location);

        return entityManager.persist(warehouse);
    }
}
