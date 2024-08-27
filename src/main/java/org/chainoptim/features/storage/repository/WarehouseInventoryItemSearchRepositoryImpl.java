package org.chainoptim.features.storage.repository;

import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.storage.model.WarehouseInventoryItem;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class WarehouseInventoryItemSearchRepositoryImpl implements WarehouseInventoryItemSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<WarehouseInventoryItem> findByWarehouseIdAdvanced(
            SearchMode searchMode,
            Integer entityId,
            SearchParams searchParams
    ) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WarehouseInventoryItem> query = builder.createQuery(WarehouseInventoryItem.class);
        Root<WarehouseInventoryItem> warehouseInventory = query.from(WarehouseInventoryItem.class);
        warehouseInventory.alias("so");

        // Perform a fetch join to load components eagerly
        warehouseInventory.fetch("component", JoinType.LEFT);

        // Add conditions (supplierId and searchQuery)
        Predicate conditions = getConditions(builder, warehouseInventory, searchMode, entityId, searchParams.getSearchQuery(), searchParams.getFilters());
        query.where(conditions);

        // Add sorting
        if (searchParams.isAscending()) {
            query.orderBy(builder.asc(warehouseInventory.get(searchParams.getSortBy())));
        } else {
            query.orderBy(builder.desc(warehouseInventory.get(searchParams.getSortBy())));
        }

        // Create query with pagination
        List<WarehouseInventoryItem> supplierOrders = entityManager.createQuery(query)
                .setFirstResult((searchParams.getPage() - 1) * searchParams.getItemsPerPage())
                .setMaxResults(searchParams.getItemsPerPage())
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<WarehouseInventoryItem> countRoot = countQuery.from(WarehouseInventoryItem.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, searchMode, entityId, searchParams.getSearchQuery(), searchParams.getFilters());
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(supplierOrders, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<WarehouseInventoryItem> root,
                                    SearchMode searchMode,
                                    Integer entityId,
                                    String searchQuery, Map<String, String> filters) {
        Predicate conditions = builder.conjunction();
        if (entityId != null) {
            String idName = searchMode == SearchMode.ORGANIZATION ? "organizationId" : "warehouseId";
            conditions = builder.and(conditions, builder.equal(root.get(idName), entityId));
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("companyId"), "%" + searchQuery + "%"));
        }

        // Filters
        conditions = addFilters(builder, root, conditions, filters);

        return conditions;
    }

    private Predicate addFilters(CriteriaBuilder builder, Root<WarehouseInventoryItem> root, Predicate conditions, Map<String, String> filters) {
        if (filters == null) {
            return conditions;
        }
        for (Map.Entry<String, String> filter : filters.entrySet()) {
            String key = filter.getKey();
            String value = filter.getValue();
            if (value == null || value.isEmpty()) {
                throw new ValidationException("Invalid filter value for " + key + " filter.");
            }

            conditions = applyFilter(builder, root, conditions, key, value);
        }

        return conditions;
    }

    private Predicate applyFilter(CriteriaBuilder builder, Root<WarehouseInventoryItem> root, Predicate conditions, String key, String value) {
        return switch (key) {
            case "createdAtStart" ->
                    addDateFilter(builder, root, conditions, "createdAt", value, true);
            case "createdAtEnd" ->
                    addDateFilter(builder, root, conditions, "createdAt", value, false);
            case "updatedAtStart" ->
                    addDateFilter(builder, root, conditions, "updatedAt", value, true);
            case "updatedAtEnd" ->
                    addDateFilter(builder, root, conditions, "updatedAt", value, false);
            case "greaterThanQuantity" ->
                    addFloatFilter(builder, root, conditions, "quantity", value, true);
            case "lessThanQuantity" ->
                    addFloatFilter(builder, root, conditions, "quantity", value, false);
            case "greaterThanMinimumRequiredQuantity" ->
                    addFloatFilter(builder, root, conditions, "minimumRequiredQuantity", value, true);
            case "lessThanMinimumRequiredQuantity" ->
                    addFloatFilter(builder, root, conditions, "minimumRequiredQuantity", value, false);
            default -> throw new ValidationException("Invalid filter: " + key);
        };
    }

    private Predicate addDateFilter(CriteriaBuilder builder, Root<WarehouseInventoryItem> root, Predicate conditions,
                                    String targetProperty, String filterValue, boolean startingAt) {
        LocalDateTime date;
        try {
            date = LocalDateTime.parse(filterValue);
        } catch (Exception e) {
            throw new ValidationException("Invalid date format for " + targetProperty + " filter.");
        }

        if (startingAt) {
            conditions = builder.and(conditions, builder.greaterThanOrEqualTo(root.get(targetProperty), date));
        } else {
            conditions = builder.and(conditions, builder.lessThanOrEqualTo(root.get(targetProperty), date));
        }

        return conditions;
    }

    private Predicate addFloatFilter(CriteriaBuilder builder, Root<WarehouseInventoryItem> root, Predicate conditions,
                                     String targetProperty, String filterValue, boolean startingAt) {
        Float value;
        try {
            value = Float.parseFloat(filterValue);
        } catch (Exception e) {
            throw new ValidationException("Invalid float format for " + targetProperty + " filter.");
        }

        if (startingAt) {
            conditions = builder.and(conditions, builder.greaterThanOrEqualTo(root.get(targetProperty), value));
        } else {
            conditions = builder.and(conditions, builder.lessThanOrEqualTo(root.get(targetProperty), value));
        }

        return conditions;
    }

}
