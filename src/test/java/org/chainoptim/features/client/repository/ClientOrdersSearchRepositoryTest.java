package org.chainoptim.features.client.repository;

import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.client.model.Client;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.enums.OrderStatus;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class ClientOrdersSearchRepositoryTest {

    @Autowired
    private ClientOrdersSearchRepositoryImpl clientOrdersSearchRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        createTestClient();
        Product product = createTestProduct();
        createTestClientOrder("no 1", "2024-01-23 12:02:02", "2024-01-23 12:02:02", product);
        createTestClientOrder("no 12", "2024-02-23 12:02:02", "2024-02-23 12:02:02", product);
        createTestClientOrder("no 3", "2024-03-23 12:02:02", "2024-03-23 12:02:02", product);
        createTestClientOrder("no 4", "2024-04-23 12:02:02", "2024-04-23 12:02:02", product);
        createTestClientOrder("no 5", "2024-05-23 12:02:02", "2024-05-23 12:02:02", product);
        createTestClientOrder("no 6", "2024-06-23 12:02:02", "2024-06-23 12:02:02", product);
        entityManager.flush();
    }

    @Test
    void findByOrganizationIdAdvanced_SearchQueryWorks() {
        // Arrange
        Integer clientId = 1;
        String searchQuery = "no 1";
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 10;

        // Act
        PaginatedResults<ClientOrder> paginatedResults = clientOrdersSearchRepository.findByClientIdAdvanced(
                clientId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(2, paginatedResults.results.size());

        // Arrange
        searchQuery = "Non-valid";

        // Act
        PaginatedResults<ClientOrder> newPaginatedResults = clientOrdersSearchRepository.findByClientIdAdvanced(
                clientId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(0, newPaginatedResults.results.size());
    }

    @Test
    void findByOrganizationIdAdvanced_PaginationWorks() {
        // Arrange
        Integer clientId = 1;
        String searchQuery = "";
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 4;

        // Act
        PaginatedResults<ClientOrder> paginatedResults = clientOrdersSearchRepository.findByClientIdAdvanced(
                clientId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(4, paginatedResults.results.size());

        // Arrange
        page = 2;

        // Act
        PaginatedResults<ClientOrder> newPaginatedResults = clientOrdersSearchRepository.findByClientIdAdvanced(
                clientId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(2, newPaginatedResults.results.size());

        assertEquals(6, paginatedResults.totalCount);
    }

    @Test
    void findByOrganizationIdAdvanced_SortOptionsWork() {
        // Arrange
        Integer clientId = 1;
        String searchQuery = "";
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 10;

        // Act
        PaginatedResults<ClientOrder> paginatedResults = clientOrdersSearchRepository.findByClientIdAdvanced(
                clientId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(1, paginatedResults.results.getFirst().getId());

        // Arrange
        ascending = false;

        // Act
        PaginatedResults<ClientOrder> newPaginatedResults = clientOrdersSearchRepository.findByClientIdAdvanced(
                clientId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(6, newPaginatedResults.results.getFirst().getId());

        // Arrange
        sortBy = "updatedAt";
        ascending = true;

        // Act
        PaginatedResults<ClientOrder> newPaginatedResults2 = clientOrdersSearchRepository.findByClientIdAdvanced(
                clientId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(1, newPaginatedResults2.results.getFirst().getId());

        // Arrange
        sortBy = "companyId";

        // Act
        PaginatedResults<ClientOrder> newPaginatedResults3 = clientOrdersSearchRepository.findByClientIdAdvanced(
                clientId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(1, newPaginatedResults3.results.getFirst().getId());
    }

    private void createTestClient() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Location location = new Location();
        location.setOrganizationId(1);
        location.setAddress("Test Address");
        entityManager.persist(location);

        Client client = Client.builder()
                .organizationId(1)
                .name("Test Client")
                .createdAt(LocalDateTime.parse("2024-01-23 12:02:02", formatter))
                .updatedAt(LocalDateTime.parse("2024-01-23 12:02:02", formatter))
                .location(location)
                .build();

        entityManager.persist(client);
    }

    private Product createTestProduct() {
        Product product = Product.builder()
                .organizationId(1)
                .name("Test Product")
                .build();

        return entityManager.persist(product);
    }

    private void createTestClientOrder(String companyId, String createdAt, String updatedAt, Product product) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ClientOrder clientOrder = ClientOrder.builder()
                .organizationId(1)
                .clientId(1)
                .companyId(companyId)
                .createdAt(LocalDateTime.parse(createdAt, formatter))
                .updatedAt(LocalDateTime.parse(updatedAt, formatter))
                .product(product)
                .status(OrderStatus.DELIVERED)
                .build();

        entityManager.persist(clientOrder);
    }
}
