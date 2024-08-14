package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;
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
import java.util.HashMap;

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

    void createTestProduct(String name, String description, String createdAt, String updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        UnitOfMeasurement unit = new UnitOfMeasurement();
        unit.setName("Unit");
        entityManager.persist(unit);

        Product product = Product.builder()
                .organizationId(1)
                .name(name)
                .description(description)
                .createdAt(LocalDateTime.parse(createdAt, formatter))
                .updatedAt(LocalDateTime.parse(updatedAt, formatter))
                .unit(unit)
                .build();

        entityManager.persist(product);
    }

    @BeforeEach
    void setUp() {
        createTestProduct("Samsung", "New phone", "2024-01-23 12:02:02", "2024-01-23 12:02:02");
        createTestProduct("Iphone 1", "New version", "2024-02-23 12:02:02", "2024-02-23 12:02:02");
        createTestProduct( "Iphone 2", "Another phone", "2024-03-23 12:02:02", "2024-03-23 12:02:02");
        createTestProduct("Google Pixel 1", "New phone", "2024-04-23 12:02:02", "2024-04-23 12:02:02");
        createTestProduct("Motorola DX1", "Old phone", "2024-05-23 12:02:02", "2024-05-23 12:02:02");
        createTestProduct("Samsung Galaxy 21", "New phone version", "2024-06-23 12:02:02", "2024-06-23 12:02:02");
        entityManager.flush();
    }

    @Test
    void findByOrganizationIdAdvanced_SearchQueryWorks() {
        // Arrange
        Integer organizationId = 1;
        SearchParams searchParams = new SearchParams("Iphone", "", new HashMap<>(), "createdAt", true, 1, 10);

        // Act
        PaginatedResults<Product> paginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchParams
        );

        // Assert
        assertEquals(2, paginatedResults.results.size());

        // Arrange
        searchParams.setSearchQuery("Non-valid");

        // Act
        PaginatedResults<Product> newPaginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchParams
        );

        // Assert
        assertEquals(0, newPaginatedResults.results.size());
    }

    @Test
    void findByOrganizationIdAdvanced_PaginationWorks() {
        // Arrange
        Integer organizationId = 1;
        SearchParams searchParams = new SearchParams("", "", new HashMap<>(), "createdAt", true, 1, 4);

        // Act
        PaginatedResults<Product> paginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchParams
        );

        // Assert
        assertEquals(4, paginatedResults.results.size());

        // Arrange
        searchParams.setPage(2);

        // Act
        PaginatedResults<Product> newPaginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchParams
        );

        // Assert
        assertEquals(2, newPaginatedResults.results.size());

        assertEquals(6, paginatedResults.totalCount);
    }

    @Test
    void findByOrganizationIdAdvanced_SortOptionsWork() {
        // Arrange
        Integer organizationId = 1;
        SearchParams searchParams = new SearchParams("", "", new HashMap<>(), "createdAt", true, 1, 10);

        // Act
        PaginatedResults<Product> paginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchParams
        );

        // Assert
        assertEquals(1, paginatedResults.results.getFirst().getId());

        // Arrange
        searchParams.setAscending(false);

        // Act
        PaginatedResults<Product> newPaginatedResults = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchParams
        );

        // Assert
        assertEquals(6, newPaginatedResults.results.getFirst().getId());

        // Arrange
        searchParams.setSortBy("updatedAt");
        searchParams.setAscending(true);

        // Act
        PaginatedResults<Product> newPaginatedResults2 = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchParams
        );

        // Assert
        assertEquals(1, newPaginatedResults2.results.getFirst().getId());

        // Arrange
        searchParams.setSortBy("name");

        // Act
        PaginatedResults<Product> newPaginatedResults3 = productsSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchParams
        );

        // Assert
        assertEquals(4, newPaginatedResults3.results.getFirst().getId());
    }
}
