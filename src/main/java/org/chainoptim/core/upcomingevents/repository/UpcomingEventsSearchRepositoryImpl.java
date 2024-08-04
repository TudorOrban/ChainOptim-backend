package org.chainoptim.core.upcomingevents.repository;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.shared.enums.OrderStatus;
import org.chainoptim.shared.search.model.SearchParams;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UpcomingEventsSearchRepositoryImpl implements UpcomingEventsSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UpcomingEvent> findByOrganizationIdAdvanced(Integer organizationId, SearchParams searchParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UpcomingEvent> query = builder.createQuery(UpcomingEvent.class);
        Root<UpcomingEvent> upcomingEventRoot = query.from(UpcomingEvent.class);

        // Add conditions (organizationId and filters)
        Predicate conditions = getConditions(builder, upcomingEventRoot, organizationId, searchParams.getFilters());
        query.where(conditions);

        query.orderBy(builder.asc(upcomingEventRoot.get("dateTime")));

        return entityManager.createQuery(query).getResultList();
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<UpcomingEvent> root,
                                    Integer organizationId, Map<String, String> filters) {
        Predicate conditions = builder.conjunction();
        conditions = builder.and(conditions, builder.equal(root.get("organizationId"), organizationId));
        conditions = addFilters(builder, root, conditions, filters);

        return conditions;
    }

    private Predicate addFilters(CriteriaBuilder builder, Root<UpcomingEvent> root, Predicate conditions, Map<String, String> filters) {
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

    private Predicate applyFilter(CriteriaBuilder builder, Root<UpcomingEvent> root, Predicate conditions, String key, String value) {
        return switch (key) {
            case "dateTimeStart" ->
                    addDateFilter(builder, root, conditions, "dateTime", value, true);
            case "dateTimeEnd" ->
                    addDateFilter(builder, root, conditions, "dateTime", value, false);
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

    private Predicate addDateFilter(CriteriaBuilder builder, Root<UpcomingEvent> root, Predicate conditions,
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

    private Predicate addFloatFilter(CriteriaBuilder builder, Root<UpcomingEvent> root, Predicate conditions,
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

    private Predicate addStatusFilter(CriteriaBuilder builder, Root<UpcomingEvent> root, Predicate conditions, String status) {
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
