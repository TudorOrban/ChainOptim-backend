package org.chainoptim.features.factory.repository;

import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.shared.commonfeatures.location.model.Location;
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
class FactoriesSearchRepositoryTest {

    @Autowired
    private FactoriesSearchRepositoryImpl factoriesSearchRepository;

    @Autowired
    private TestEntityManager entityManager;

    void createTestFactory(String name, String createdAt, String updatedAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Location location = new Location();
        location.setOrganizationId(1);
        location.setAddress("Test Address");
        entityManager.persist(location);

        Factory factory = Factory.builder()
                .organizationId(1)
                .name(name)
                .createdAt(LocalDateTime.parse(createdAt, formatter))
                .updatedAt(LocalDateTime.parse(updatedAt, formatter))
                .location(location)
                .build();

        entityManager.persist(factory);
    }

    @BeforeEach
    void setUp() {
        createTestFactory("Factory no 1", "2024-01-23 12:02:02", "2024-01-23 12:02:02");
        createTestFactory("Factory no 12", "2024-02-23 12:02:02", "2024-02-23 12:02:02");
        createTestFactory("Factory no 3", "2024-03-23 12:02:02", "2024-03-23 12:02:02");
        createTestFactory("Factory no 4", "2024-04-23 12:02:02", "2024-04-23 12:02:02");
        createTestFactory("Factory no 5", "2024-05-23 12:02:02", "2024-05-23 12:02:02");
        createTestFactory("Factory no 6", "2024-06-23 12:02:02", "2024-06-23 12:02:02");
        entityManager.flush();
    }

    @Test
    void findByOrganizationIdAdvanced_SearchQueryWorks() {
        // Arrange
        Integer organizationId = 1;
        String searchQuery = "Factory no 1";
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 10;

        // Act
        PaginatedResults<Factory> paginatedResults = factoriesSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(2, paginatedResults.results.size());

        // Arrange
        searchQuery = "Non-valid";

        // Act
        PaginatedResults<Factory> newPaginatedResults = factoriesSearchRepository.findByOrganizationIdAdvanced(
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
        PaginatedResults<Factory> paginatedResults = factoriesSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(4, paginatedResults.results.size());

        // Arrange
        page = 2;

        // Act
        PaginatedResults<Factory> newPaginatedResults = factoriesSearchRepository.findByOrganizationIdAdvanced(
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
        PaginatedResults<Factory> paginatedResults = factoriesSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(1, paginatedResults.results.getFirst().getId());

        // Arrange
        ascending = false;

        // Act
        PaginatedResults<Factory> newPaginatedResults = factoriesSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(6, newPaginatedResults.results.getFirst().getId());

        // Arrange
        sortBy = "updatedAt";
        ascending = true;

        // Act
        PaginatedResults<Factory> newPaginatedResults2 = factoriesSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(1, newPaginatedResults2.results.getFirst().getId());

        // Arrange
        sortBy = "name";

        // Act
        PaginatedResults<Factory> newPaginatedResults3 = factoriesSearchRepository.findByOrganizationIdAdvanced(
                organizationId, searchQuery, sortBy, ascending, page, itemsPerPage
        );

        // Assert
        assertEquals(1, newPaginatedResults3.results.getFirst().getId());
    }
}
