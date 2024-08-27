package org.chainoptim.features.goods.transportroute.repository;

import org.chainoptim.features.goods.transportroute.model.ResourceTransportRoute;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;

@Repository
public class TransportRouteSearchRepositoryImpl implements TransportRouteSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<ResourceTransportRoute> findByOrganizationIdAdvanced(Integer organizationId, SearchParams searchParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ResourceTransportRoute> query = builder.createQuery(ResourceTransportRoute.class);
        Root<ResourceTransportRoute> product = query.from(ResourceTransportRoute.class);

        // Add conditions (organizationId and searchQuery)
        Predicate conditions = getConditions(builder, product, organizationId, searchParams.getSearchQuery());
        query.where(conditions);

        // Add sorting
        if (searchParams.isAscending()) {
            query.orderBy(builder.asc(product.get(searchParams.getSortBy())));
        } else {
            query.orderBy(builder.desc(product.get(searchParams.getSortBy())));
        }

        // Create query with pagination
        List<ResourceTransportRoute> products = entityManager.createQuery(query)
                .setFirstResult((searchParams.getPage() - 1) * searchParams.getItemsPerPage())
                .setMaxResults(searchParams.getItemsPerPage())
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<ResourceTransportRoute> countRoot = countQuery.from(ResourceTransportRoute.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, organizationId, searchParams.getSearchQuery()));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(products, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<ResourceTransportRoute> root, Integer organizationId, String searchQuery) {
        Predicate conditions = builder.conjunction();
        if (organizationId != null) {
            conditions = builder.and(conditions, builder.equal(root.get("organizationId"), organizationId));
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("companyId"), "%" + searchQuery + "%"));
        }
        return conditions;
    }
}
