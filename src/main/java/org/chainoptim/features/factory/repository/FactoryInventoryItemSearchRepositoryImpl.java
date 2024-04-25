package org.chainoptim.features.factory.repository;

import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class FactoryInventoryItemSearchRepositoryImpl implements FactoryInventoryItemSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<FactoryInventoryItem> findFactoryItemsByIdAdvanced(
            Integer factoryId,
            String searchQuery, Map<String, String> filters,
            String sortBy, boolean ascending,
            int page, int itemsPerPage
    ) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FactoryInventoryItem> query = builder.createQuery(FactoryInventoryItem.class);
        Root<FactoryInventoryItem> factoryInventoryItem = query.from(FactoryInventoryItem.class);
        factoryInventoryItem.alias("so");

        // Perform a fetch join to load products eagerly
        factoryInventoryItem.fetch("product", JoinType.LEFT);
        factoryInventoryItem.fetch("component", JoinType.LEFT);

        // Add conditions (factoryId, searchQuery and filters)
        Predicate conditions = getConditions(builder, factoryInventoryItem, factoryId, searchQuery, filters);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(factoryInventoryItem.get(sortBy)));
        } else {
            query.orderBy(builder.desc(factoryInventoryItem.get(sortBy)));
        }

        // Create query with pagination
        List<FactoryInventoryItem> factoryInventoryItems = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<FactoryInventoryItem> countRoot = countQuery.from(FactoryInventoryItem.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, factoryId, searchQuery, filters);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(factoryInventoryItems, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<FactoryInventoryItem> root,
                                    Integer factoryId,
                                    String searchQuery, Map<String, String> filters) {
        Predicate conditions = builder.conjunction();
        if (factoryId != null) {
            conditions = builder.and(conditions, builder.equal(root.get("factoryId"), factoryId));
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("companyId"), "%" + searchQuery + "%"));
        }

        // Filters
        conditions = addFilters(builder, root, conditions, filters);

        return conditions;
    }

    private Predicate addFilters(CriteriaBuilder builder, Root<FactoryInventoryItem> root, Predicate conditions, Map<String, String> filters) {
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

    private Predicate applyFilter(CriteriaBuilder builder, Root<FactoryInventoryItem> root, Predicate conditions, String key, String value) {
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

    private Predicate addDateFilter(CriteriaBuilder builder, Root<FactoryInventoryItem> root, Predicate conditions,
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

    private Predicate addFloatFilter(CriteriaBuilder builder, Root<FactoryInventoryItem> root, Predicate conditions,
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
