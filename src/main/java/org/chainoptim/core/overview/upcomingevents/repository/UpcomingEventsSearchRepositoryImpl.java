package org.chainoptim.core.overview.upcomingevents.repository;

import org.chainoptim.core.overview.upcomingevents.model.UpcomingEvent;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.shared.enums.Feature;
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
            case "associatedEntityType" ->
                    addFeatureFilter(builder, root, conditions, "associatedEntityType", value);
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

    private Predicate addFeatureFilter(CriteriaBuilder builder, Root<UpcomingEvent> root, Predicate conditions,
                                       String targetProperty, String filterValue) {
        Feature feature;
        try {
            feature = Feature.fromString(filterValue);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid feature filter.");
        }

        return builder.and(conditions, builder.equal(root.get(targetProperty), feature));
    }
}
