package org.chainoptim.features.factory.repository;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.model.SubscriptionPlanTier;
import org.chainoptim.features.factory.model.Factory;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class FactoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FactoryRepository factoryRepository;

    Integer organizationId;
    Integer locationId;
    Integer factoryId;

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

        // Set up factory for update and delete tests
        Factory factory = addTestFactory();
        factoryId = factory.getId();
    }

    @Test
    void testCreateFactory() {
        // Arrange
        Factory savedFactory = addTestFactory();

        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<Factory> foundFactoryOpt = factoryRepository.findById(savedFactory.getId());
        assertTrue(foundFactoryOpt.isPresent(), "Factory should be found in the database");

        Factory foundFactory = foundFactoryOpt.get();
        assertEquals(savedFactory.getName(), foundFactory.getName());
        assertEquals(savedFactory.getOrganizationId(), foundFactory.getOrganizationId());
        assertEquals(savedFactory.getLocation().getId(), foundFactory.getLocation().getId());
    }

    @Test
    void testUpdateFactory() {
        // Arrange
        Optional<Factory> factoryOptional = factoryRepository.findById(factoryId); // Id from setUp
        if (factoryOptional.isEmpty()) {
            fail("Expected an existing factory with id " + factoryOptional);
        }

        Factory factory = factoryOptional.get();
        factory.setName("New Test Name");

        // Act
        Factory updatedFactory = factoryRepository.save(factory);

        // Assert
        assertNotNull(updatedFactory);
        assertEquals("New Test Name", updatedFactory.getName());
    }

    @Test
    void testDeleteFactory() {
        // Arrange
        Optional<Factory> factoryToBeDeletedOptional = factoryRepository.findById(factoryId);
        if (factoryToBeDeletedOptional.isEmpty()) {
            fail("Expected an existing factory with id " + factoryId);
        }

        Factory factoryToBeDeleted = factoryToBeDeletedOptional.get();

        // Act
        factoryRepository.delete(factoryToBeDeleted);

        // Assert
        Optional<Factory> deletedFactoryOptional = factoryRepository.findById(factoryId);
        if (deletedFactoryOptional.isPresent()) {
            fail("Expected factory with id 1 to have been deleted");
        }
    }

    Factory addTestFactory() {
        Factory factory = new Factory();
        factory.setName("Test Factory");
        factory.setOrganizationId(organizationId);
        Location location = new Location();
        location.setId(locationId);
        factory.setLocation(location);

        return entityManager.persist(factory);
    }
}
