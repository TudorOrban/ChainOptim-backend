package org.chainoptim.features.client.repository;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.features.client.model.Client;
import org.chainoptim.features.client.repository.ClientRepository;
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
class ClientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    Integer organizationId;
    Integer locationId;
    Integer clientId;

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

        // Set up client for update and delete tests
        Client client = addTestClient();
        clientId = client.getId();
    }

    @Test
    void testCreateClient() {
        // Arrange
        Client savedClient = addTestClient();

        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<Client> foundClientOpt = clientRepository.findById(savedClient.getId());
        assertTrue(foundClientOpt.isPresent(), "Client should be found in the database");

        Client foundClient = foundClientOpt.get();
        assertEquals(savedClient.getName(), foundClient.getName());
        assertEquals(savedClient.getOrganizationId(), foundClient.getOrganizationId());
        assertEquals(savedClient.getLocation().getId(), foundClient.getLocation().getId());
    }

    @Test
    void testUpdateClient() {
        // Arrange
        Optional<Client> clientOptional = clientRepository.findById(clientId); // Id from setUp
        if (clientOptional.isEmpty()) {
            fail("Expected an existing client with id " + clientOptional);
        }

        Client client = clientOptional.get();
        client.setName("New Test Name");

        // Act
        Client updatedClient = clientRepository.save(client);

        // Assert
        assertNotNull(updatedClient);
        assertEquals("New Test Name", updatedClient.getName());
    }

    @Test
    void testDeleteClient() {
        // Arrange
        Optional<Client> clientToBeDeletedOptional = clientRepository.findById(clientId);
        if (clientToBeDeletedOptional.isEmpty()) {
            fail("Expected an existing client with id " + clientId);
        }

        Client clientToBeDeleted = clientToBeDeletedOptional.get();

        // Act
        clientRepository.delete(clientToBeDeleted);

        // Assert
        Optional<Client> deletedClientOptional = clientRepository.findById(clientId);
        if (deletedClientOptional.isPresent()) {
            fail("Expected client with id 1 to have been deleted");
        }
    }

    Client addTestClient() {
        Client client = new Client();
        client.setName("Test Client");
        client.setOrganizationId(organizationId);
        Location location = new Location();
        location.setId(locationId);
        client.setLocation(location);

        return entityManager.persist(client);
    }
}
