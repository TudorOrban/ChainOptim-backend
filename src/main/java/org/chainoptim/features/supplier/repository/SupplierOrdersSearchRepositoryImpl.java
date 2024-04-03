package org.chainoptim.features.supplier.repository;

import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.List;

public class SupplierOrdersSearchRepositoryImpl implements SupplierOrdersSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<SupplierOrder> findBySupplierIdAdvanced(Integer supplierId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SupplierOrder> query = builder.createQuery(SupplierOrder.class);
        Root<SupplierOrder> supplierOrder = query.from(SupplierOrder.class);

        // Perform a fetch join to load components eagerly
        supplierOrder.fetch("component", JoinType.LEFT);

        // Add conditions (supplierId and searchQuery)
        Predicate conditions = getConditions(builder, supplierOrder, supplierId, searchQuery);
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
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, supplierId, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(supplierOrders, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<SupplierOrder> root, Integer supplierId, String searchQuery) {
        Predicate conditions = builder.conjunction();
        if (supplierId != null) {
            conditions = builder.and(conditions, builder.equal(root.get("supplierId"), supplierId));
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("companyId"), "%" + searchQuery + "%"));
        }
        return conditions;
    }
}
