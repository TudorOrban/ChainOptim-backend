package org.chainoptim.features.storage.repository;

import org.chainoptim.features.storage.inventory.repository.WarehouseInventoryItemSearchRepositoryImpl;
import org.chainoptim.features.storage.warehouse.model.Warehouse;
import org.chainoptim.features.storage.inventory.model.WarehouseInventoryItem;
import org.chainoptim.features.goods.component.model.Component;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.enums.SearchMode;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class WarehouseInventoryItemSearchRepositoryTest {

    @Autowired
    private WarehouseInventoryItemSearchRepositoryImpl warehouseInventoryItemsSearchRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        createTestWarehouse();
        Component component = createTestComponent();
        createTestWarehouseInventoryItem("no 1", "2024-01-23 12:02:02", "2024-01-23 12:02:02", component, 1f);
        createTestWarehouseInventoryItem("no 12", "2024-02-23 12:02:02", "2024-02-23 12:02:02", component, 2f);
        createTestWarehouseInventoryItem("no 3", "2024-03-23 12:02:02", "2024-03-23 12:02:02", component, 3f);
        createTestWarehouseInventoryItem("no 4", "2024-04-23 12:02:02", "2024-04-23 12:02:02", component, 4f);
        createTestWarehouseInventoryItem("no 5", "2024-05-23 12:02:02", "2024-05-23 12:02:02", component, 5f);
        createTestWarehouseInventoryItem("no 6", "2024-06-23 12:02:02", "2024-06-23 12:02:02", component, 6f);
        entityManager.flush();
    }

    @Test
    void findByOrganizationIdAdvanced_SearchQueryWorks() {
        // Arrange
        Integer warehouseId = 1;
        String searchQuery = "no 1";
        Map<String, String> filters = new HashMap<>();
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 10;
        SearchParams searchParams = new SearchParams(searchQuery, "{}", filters, sortBy, ascending, page, itemsPerPage);

        // Act
        PaginatedResults<WarehouseInventoryItem> paginatedResults = warehouseInventoryItemsSearchRepository.findByWarehouseIdAdvanced(
                SearchMode.SECONDARY, warehouseId, searchParams
        );

        // Assert
        assertEquals(2, paginatedResults.results.size());

        // Arrange
        searchParams.setSearchQuery("Non-valid");

        // Act
        PaginatedResults<WarehouseInventoryItem> newPaginatedResults = warehouseInventoryItemsSearchRepository.findByWarehouseIdAdvanced(
                SearchMode.SECONDARY, warehouseId, searchParams
        );

        // Assert
        assertEquals(0, newPaginatedResults.results.size());
    }

    @Test
    void findByOrganizationIdAdvanced_PaginationWorks() {
        // Arrange
        Integer warehouseId = 1;
        String searchQuery = "";
        Map<String, String> filters = new HashMap<>();
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 4;
        SearchParams searchParams = new SearchParams(searchQuery, "{}", filters, sortBy, ascending, page, itemsPerPage);

        // Act
        PaginatedResults<WarehouseInventoryItem> paginatedResults = warehouseInventoryItemsSearchRepository.findByWarehouseIdAdvanced(
                SearchMode.SECONDARY, warehouseId, searchParams
        );

        // Assert
        assertEquals(4, paginatedResults.results.size());

        // Arrange
        searchParams.setPage(2);

        // Act
        PaginatedResults<WarehouseInventoryItem> newPaginatedResults = warehouseInventoryItemsSearchRepository.findByWarehouseIdAdvanced(
                SearchMode.SECONDARY, warehouseId, searchParams
        );

        // Assert
        assertEquals(2, newPaginatedResults.results.size());

        assertEquals(6, paginatedResults.totalCount);
    }

    @Test
    void findByOrganizationIdAdvanced_SortOptionsWork() {
        // Arrange
        Integer warehouseId = 1;
        String searchQuery = "";
        Map<String, String> filters = new HashMap<>();
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 10;
        SearchParams searchParams = new SearchParams(searchQuery, "{}", filters, sortBy, ascending, page, itemsPerPage);

        // Act
        PaginatedResults<WarehouseInventoryItem> paginatedResults = warehouseInventoryItemsSearchRepository.findByWarehouseIdAdvanced(
                SearchMode.SECONDARY, warehouseId, searchParams
        );

        // Assert
        assertEquals(1, paginatedResults.results.getFirst().getId());

        // Arrange
        searchParams.setAscending(false);

        // Act
        PaginatedResults<WarehouseInventoryItem> newPaginatedResults = warehouseInventoryItemsSearchRepository.findByWarehouseIdAdvanced(
                SearchMode.SECONDARY, warehouseId, searchParams
        );

        // Assert
        assertEquals(6, newPaginatedResults.results.getFirst().getId());

        // Arrange
        searchParams.setSortBy("updatedAt");
        searchParams.setAscending(true);

        // Act
        PaginatedResults<WarehouseInventoryItem> newPaginatedResults2 = warehouseInventoryItemsSearchRepository.findByWarehouseIdAdvanced(
                SearchMode.SECONDARY, warehouseId, searchParams
        );

        // Assert
        assertEquals(1, newPaginatedResults2.results.getFirst().getId());

        // Arrange
        searchParams.setSortBy("companyId");

        // Act
        PaginatedResults<WarehouseInventoryItem> newPaginatedResults3 = warehouseInventoryItemsSearchRepository.findByWarehouseIdAdvanced(
                SearchMode.SECONDARY, warehouseId, searchParams
        );

        // Assert
        assertEquals(1, newPaginatedResults3.results.getFirst().getId());
    }
    
    @Test
    void findByOrganizationIdAdvanced_FiltersWork() {
        // Arrange
        Integer warehouseId = 1;
        String searchQuery = "";
        Map<String, String> filters = new HashMap<>();
        filters.put("createdAtEnd", "2023-01-01T00:00:00");
        String sortBy = "createdAt";
        boolean ascending = true;
        int page = 1;
        int itemsPerPage = 10;
        SearchParams searchParams = new SearchParams(searchQuery, "{}", filters, sortBy, ascending, page, itemsPerPage);

        // Act
        PaginatedResults<WarehouseInventoryItem> newPaginatedResults2 = warehouseInventoryItemsSearchRepository.findByWarehouseIdAdvanced(
                SearchMode.SECONDARY, warehouseId, searchParams
        );

        // Assert
        assertEquals(0, newPaginatedResults2.results.size());

        // Arrange
        filters.clear();
        filters.put("greaterThanQuantity", "3");
        searchParams.setFilters(filters);

        // Act
        PaginatedResults<WarehouseInventoryItem> newPaginatedResults3 = warehouseInventoryItemsSearchRepository.findByWarehouseIdAdvanced(
                SearchMode.SECONDARY, warehouseId, searchParams
        );

        // Assert
        assertEquals(4, newPaginatedResults3.results.size());
    }

    private void createTestWarehouse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Location location = new Location();
        location.setOrganizationId(1);
        location.setAddress("Test Address");
        entityManager.persist(location);

        Warehouse warehouse = Warehouse.builder()
                .organizationId(1)
                .name("Test Warehouse")
                .createdAt(LocalDateTime.parse("2024-01-23 12:02:02", formatter))
                .updatedAt(LocalDateTime.parse("2024-01-23 12:02:02", formatter))
                .location(location)
                .build();

        entityManager.persist(warehouse);
    }

    private Component createTestComponent() {
        Component component = Component.builder()
                .organizationId(1)
                .name("Test Component")
                .build();

        return entityManager.persist(component);
    }

    private void createTestWarehouseInventoryItem(String companyId, String createdAt, String updatedAt, Component component, float quantity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        WarehouseInventoryItem warehouseInventoryItem = WarehouseInventoryItem.builder()
                .organizationId(1)
                .warehouseId(1)
                .companyId(companyId)
                .createdAt(LocalDateTime.parse(createdAt, formatter))
                .updatedAt(LocalDateTime.parse(updatedAt, formatter))
                .component(component)
                .quantity(quantity)
                .build();

        entityManager.persist(warehouseInventoryItem);
    }
}
