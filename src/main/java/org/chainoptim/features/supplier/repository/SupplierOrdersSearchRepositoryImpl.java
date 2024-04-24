package org.chainoptim.features.supplier.repository;

import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.shared.enums.OrderStatus;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierOrdersSearchRepositoryImpl implements SupplierOrdersSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<SupplierOrder> findBySupplierIdAdvanced(
            Integer supplierId,
            String searchQuery, Map<String, String> filters,
            String sortBy, boolean ascending,
            int page, int itemsPerPage
    ) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SupplierOrder> query = builder.createQuery(SupplierOrder.class);
        Root<SupplierOrder> supplierOrder = query.from(SupplierOrder.class);
        supplierOrder.alias("so");

        // Perform a fetch join to load components eagerly
        supplierOrder.fetch("component", JoinType.LEFT);

        // Add conditions (supplierId and searchQuery)
        Predicate conditions = getConditions(builder, supplierOrder, supplierId, searchQuery, filters);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(supplierOrder.get(sortBy)));
        } else {
            query.orderBy(builder.desc(supplierOrder.get(sortBy)));
        }

        // Create query with pagination
        List<SupplierOrder> supplierOrders = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<SupplierOrder> countRoot = countQuery.from(SupplierOrder.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, supplierId, searchQuery, filters);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(supplierOrders, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<SupplierOrder> root,
                                    Integer supplierId,
                                    String searchQuery, Map<String, String> filters) {
        Predicate conditions = builder.conjunction();
        if (supplierId != null) {
            conditions = builder.and(conditions, builder.equal(root.get("supplierId"), supplierId));
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("companyId"), "%" + searchQuery + "%"));
        }

        // Filters
        conditions = addFilters(builder, root, conditions, filters);

        return conditions;
    }

    private Predicate addFilters(CriteriaBuilder builder, Root<SupplierOrder> root, Predicate conditions, Map<String, String> filters) {
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

    private Predicate applyFilter(CriteriaBuilder builder, Root<SupplierOrder> root, Predicate conditions, String key, String value) {
        return switch (key) {
            case "orderDateStart" ->
                    addDateFilter(builder, root, conditions, "orderDate", value, true);
            case "orderDateEnd" ->
                    addDateFilter(builder, root, conditions, "orderDate", value, false);
            case "deliveryDateStart" ->
                    addDateFilter(builder, root, conditions, "deliveryDate", value, true);
            case "deliveryDateEnd" ->
                    addDateFilter(builder, root, conditions, "deliveryDate", value, false);
            case "estimatedDeliveryDateStart" ->
                    addDateFilter(builder, root, conditions, "estimatedDeliveryDate", value, true);
            case "estimatedDeliveryDateEnd" ->
                    addDateFilter(builder, root, conditions, "estimatedDeliveryDate", value, false);
            case "greaterThanQuantity" ->
                    addFloatFilter(builder, root, conditions, "quantity", value, true);
            case "lessThanQuantity" ->
                    addFloatFilter(builder, root, conditions, "quantity", value, false);
            case "greaterThanDeliveredQuantity" ->
                    addFloatFilter(builder, root, conditions, "deliveredQuantity", value, true);
            case "lessThanDeliveredQuantity" ->
                    addFloatFilter(builder, root, conditions, "deliveredQuantity", value, false);
            case "status" ->
                    addStatusFilter(builder, root, conditions, value);
            default -> throw new ValidationException("Invalid filter: " + key);
        };
    }

    private Predicate addDateFilter(CriteriaBuilder builder, Root<SupplierOrder> root, Predicate conditions,
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

    private Predicate addFloatFilter(CriteriaBuilder builder, Root<SupplierOrder> root, Predicate conditions,
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

    private Predicate addStatusFilter(CriteriaBuilder builder, Root<SupplierOrder> root, Predicate conditions, String status) {
        if (status == null || status.isEmpty()) return conditions;

        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid status filter.");
        }

        return builder.and(conditions, builder.equal(root.get("status"), orderStatus));
    }

}
