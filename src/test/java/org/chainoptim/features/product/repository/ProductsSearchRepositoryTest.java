package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.Product;
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
class ProductsSearchRepositoryTest {

    @Autowired
    private ProductsSearchRepositoryImpl productsSearchRepository;

    @Autowired
    private TestEntityManager entityManager;

    void createTestProduct(Integer id, String name, String description, String createdAt, String updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Product product = Product.builder()
                .id(id)
                .organizationId(1)
                .name(name)
                .description(description)
                .createdAt(LocalDateTime.parse(createdAt, formatter))
                .updatedAt(LocalDateTime.parse(updatedAt, formatter))
                .unitId(1)
                .build();

        entityManager.persist(product);
    }

    @BeforeEach
    void setUp() {
        createTestProduct(1, "Samsung", "New phone", "2024-01-23 12:02:02", "2024-01-23 12:02:02");
        createTestProduct(2, "Iphone 1", "New version", "2024-02-23 12:02:02", "2024-02-23 12:02:02");
        createTestProduct(3, "Iphone 2", "Another phone", "2024-03-23 12:02:02", "2024-03-23 12:02:02");
        createTestProduct(4, "Google Pixel 1", "New phone", "2024-04-23 12:02:02", "2024-04-23 12:02:02");
        createTestProduct(5, "Motorola DX1", "Old phone", "2024-05-23 12:02:02", "2024-05-23 12:02:02");
        createTestProduct(6, "Samsung Galaxy 21", "New phone version", "2024-06-23 12:02:02", "2024-06-23 12:02:02");
        entityManager.flush();
    }

    @Test
    void findByOrganizationIdAdvanced_SearchQueryWorks() {
        // Arrange
        Integer organizationId = 1;
        String searchQuery = "Iphone";
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 10;

        // Act
        PaginatedResults<Product> paginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(2, paginatedResults.results.size());

        // Arrange
        searchQuery = "Non-valid";

        // Act
        PaginatedResults<Product> newPaginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(0, newPaginatedResults.results.size());
    }

    @Test
    void findByOrganizationIdAdvanced_PaginationWorks() {
        // Arrange
        Integer organizationId = 1;
        String searchQuery = "";
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 4;

        // Act
        PaginatedResults<Product> paginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(4, paginatedResults.results.size());

        // Arrange
        page = 2;

        // Act
        PaginatedResults<Product> newPaginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(2, newPaginatedResults.results.size());

        assertEquals(6, paginatedResults.totalCount);
    }

    @Test
    void findByOrganizationIdAdvanced_SortOptionsWork() {
        // Arrange
        Integer organizationId = 1;
        String searchQuery = "";
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 10;

        // Act
        PaginatedResults<Product> paginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(1, paginatedResults.results.getFirst().getId());

        // Arrange
        ascending = false;

        // Act
        PaginatedResults<Product> newPaginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(6, newPaginatedResults.results.getFirst().getId());

        // Arrange
        sortBy = "updatedAt";
        ascending = true;

        // Act
        PaginatedResults<Product> newPaginatedResults2 = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(1, newPaginatedResults2.results.getFirst().getId());

        // Arrange
        sortBy = "name";

        // Act
        PaginatedResults<Product> newPaginatedResults3 = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(4, newPaginatedResults3.results.getFirst().getId());
    }
}
