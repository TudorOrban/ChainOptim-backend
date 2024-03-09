package org.chainoptim.features.factory.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.ArrayList;
import java.util.List;

public class FactoriesSearchRepositoryImpl implements FactoriesSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<Factory> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Factory> query = builder.createQuery(Factory.class);
        Root<Factory> factory = query.from(Factory.class);

        // Add conditions (organizationId and searchQuery)
        Predicate conditions = getFactorySearchConditions(builder, factory, organizationId, searchQuery);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(factory.get(sortBy)));
        } else {
            query.orderBy(builder.desc(factory.get(sortBy)));
        }

        // Create query with pagination
        List<Factory> factories = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<Factory> countRoot = countQuery.from(Factory.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getFactorySearchConditions(countBuilder, countRoot, organizationId, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(factories, totalCount);
    }

    private Predicate getFactorySearchConditions(CriteriaBuilder builder, Root<Factory> root, Integer organizationId, String searchQuery) {
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
