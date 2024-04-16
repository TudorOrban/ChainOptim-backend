package org.chainoptim.features.supplier.repository;

import org.chainoptim.features.supplier.model.SupplierShipment;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.List;

public class SupplierShipmentsSearchRepositoryImpl implements SupplierShipmentsSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<SupplierShipment> findBySupplierOrderIdAdvanced(Integer supplierOrderId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SupplierShipment> query = builder.createQuery(SupplierShipment.class);
        Root<SupplierShipment> supplierShipment = query.from(SupplierShipment.class);

        // Add conditions (supplierId and searchQuery)
        Predicate conditions = getConditions(builder, supplierShipment, supplierOrderId, searchQuery);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(supplierShipment.get(sortBy)));
        } else {
            query.orderBy(builder.desc(supplierShipment.get(sortBy)));
        }

        // Create query with pagination
        List<SupplierShipment> supplierShipments = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<SupplierShipment> countRoot = countQuery.from(SupplierShipment.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, supplierOrderId, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(supplierShipments, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<SupplierShipment> root, Integer supplierOrderId, String searchQuery) {
        Predicate conditions = builder.conjunction();
        if (supplierOrderId != null) {
            conditions = builder.and(conditions, builder.equal(root.get("supplierOrderId"), supplierOrderId));
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("supplierOrderId"), "%" + searchQuery + "%"));
        }
        return conditions;
    }
}
