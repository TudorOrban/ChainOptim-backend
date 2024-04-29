package org.chainoptim.features.productpipeline.repository;

import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.shared.search.model.PaginatedResults;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class ComponentsSearchRepositoryImpl implements ComponentsSearchRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResults<Component> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Component> query = builder.createQuery(Component.class);
        Root<Component> component = query.from(Component.class);

        // Add conditions (organizationId and searchQuery)
        Predicate conditions = getConditions(builder, component, organizationId, searchQuery);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(component.get(sortBy)));
        } else {
            query.orderBy(builder.desc(component.get(sortBy)));
        }

        // Create query with pagination
        List<Component> components = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<Component> countRoot = countQuery.from(Component.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, organizationId, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(components, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<Component> root, Integer organizationId, String searchQuery) {
        Predicate conditions = builder.conjunction();
        if (organizationId != null) {
            conditions = builder.and(conditions, builder.equal(root.get("organizationId"), organizationId));
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("name"), "%" + searchQuery + "%"));
        }
        return conditions;
    }
}
