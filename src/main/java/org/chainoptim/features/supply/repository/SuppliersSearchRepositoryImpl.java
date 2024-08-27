package org.chainoptim.features.supply.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public class SuppliersSearchRepositoryImpl implements SuppliersSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<Supplier> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Supplier> query = builder.createQuery(Supplier.class);
        Root<Supplier> supplier = query.from(Supplier.class);

        // Add conditions (organizationId and searchQuery)
        Predicate conditions = getConditions(builder, supplier, organizationId, searchQuery);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(supplier.get(sortBy)));
        } else {
            query.orderBy(builder.desc(supplier.get(sortBy)));
        }

        // Create query with pagination
        List<Supplier> suppliers = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<Supplier> countRoot = countQuery.from(Supplier.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, organizationId, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(suppliers, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<Supplier> root, Integer organizationId, String searchQuery) {
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
