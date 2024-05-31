package org.chainoptim.features.client.repository;

import jakarta.persistence.criteria.*;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.client.model.ClientShipment;
import org.chainoptim.shared.enums.OrderStatus;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.chainoptim.shared.search.model.SearchParams;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ClientShipmentsSearchRepositoryImpl implements ClientShipmentsSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<ClientShipment> findByClientIdAdvanced(
            SearchMode searchMode, Integer entityId, SearchParams searchParams) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientShipment> query = builder.createQuery(ClientShipment.class);
        Root<ClientShipment> clientShipment = query.from(ClientShipment.class);

        // Add conditions (clientId and searchQuery)
        Predicate conditions = getConditions(builder, clientShipment, searchMode, entityId, searchParams.getSearchQuery(), searchParams.getFilters());
        query.where(conditions);

        // Add sorting
        if (searchParams.isAscending()) {
            query.orderBy(builder.asc(clientShipment.get(searchParams.getSortBy())));
        } else {
            query.orderBy(builder.desc(clientShipment.get(searchParams.getSortBy())));
        }

        // Create query with pagination
        List<ClientShipment> clientShipments = entityManager.createQuery(query)
                .setFirstResult((searchParams.getPage() - 1) * searchParams.getItemsPerPage())
                .setMaxResults(searchParams.getItemsPerPage())
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<ClientShipment> countRoot = countQuery.from(ClientShipment.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, searchMode, entityId, searchParams.getSearchQuery(), searchParams.getFilters());
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(clientShipments, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<ClientShipment> root,
                                    SearchMode searchMode,
                                    Integer entityId,
                                    String searchQuery, Map<String, String> filters) {
        Predicate conditions = builder.conjunction();
        if (entityId != null) {
            String idName = searchMode == SearchMode.ORGANIZATION ? "organizationId" : "clientId";
            conditions = builder.and(conditions, builder.equal(root.get(idName), entityId));
        }
        
        // Filters
        conditions = addFilters(builder, root, conditions, filters);

        return conditions;
    }

    private Predicate addFilters(CriteriaBuilder builder, Root<ClientShipment> root, Predicate conditions, Map<String, String> filters) {
        if (filters == null) return conditions;
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

    private Predicate applyFilter(CriteriaBuilder builder, Root<ClientShipment> root, Predicate conditions, String key, String value) {
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
    private Predicate addDateFilter(CriteriaBuilder builder, Root<ClientShipment> root, Predicate conditions,
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

    private Predicate addFloatFilter(CriteriaBuilder builder, Root<ClientShipment> root, Predicate conditions,
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

    private Predicate addStatusFilter(CriteriaBuilder builder, Root<ClientShipment> root, Predicate conditions, String status) {
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
