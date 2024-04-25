package org.chainoptim.features.warehouse.repository;

import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.shared.search.model.PaginatedResults;

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
    public PaginatedResults<WarehouseInventoryItem> findWarehouseItemsByIdAdvanced(
            Integer warehouseId,
            String searchQuery, Map<String, String> filters,
            String sortBy, boolean ascending,
            int page, int itemsPerPage
    ) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WarehouseInventoryItem> query = builder.createQuery(WarehouseInventoryItem.class);
        Root<WarehouseInventoryItem> warehouseInventoryItem = query.from(WarehouseInventoryItem.class);
        warehouseInventoryItem.alias("so");

        // Perform a fetch join to load products eagerly
        warehouseInventoryItem.fetch("product", JoinType.LEFT);
        warehouseInventoryItem.fetch("component", JoinType.LEFT);

        // Add conditions (warehouseId, searchQuery and filters)
        Predicate conditions = getConditions(builder, warehouseInventoryItem, warehouseId, searchQuery, filters);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(warehouseInventoryItem.get(sortBy)));
        } else {
            query.orderBy(builder.desc(warehouseInventoryItem.get(sortBy)));
        }

        // Create query with pagination
        List<WarehouseInventoryItem> warehouseInventoryItems = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<WarehouseInventoryItem> countRoot = countQuery.from(WarehouseInventoryItem.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, warehouseId, searchQuery, filters);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(warehouseInventoryItems, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<WarehouseInventoryItem> root,
                                    Integer warehouseId,
                                    String searchQuery, Map<String, String> filters) {
        Predicate conditions = builder.conjunction();
        if (warehouseId != null) {
            conditions = builder.and(conditions, builder.equal(root.get("warehouseId"), warehouseId));
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("companyId"), "%" + searchQuery + "%"));
        }

        // Filters
        conditions = addFilters(builder, root, conditions, filters);

        return conditions;
    }

    private Predicate addFilters(CriteriaBuilder builder, Root<WarehouseInventoryItem> root, Predicate conditions, Map<String, String> filters) {
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
