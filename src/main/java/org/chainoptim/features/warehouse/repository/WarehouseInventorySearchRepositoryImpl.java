package org.chainoptim.features.warehouse.repository;

import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class WarehouseInventorySearchRepositoryImpl implements WarehouseInventorySearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<WarehouseInventoryItem> findWarehouseItemsById(
            Integer warehouseId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    ) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WarehouseInventoryItem> query = builder.createQuery(WarehouseInventoryItem.class);
        Root<WarehouseInventoryItem> root = query.from(WarehouseInventoryItem.class);

        // Skip if no warehouseId (add error in the future)
        if (warehouseId == null) {
            return new PaginatedResults<>(new ArrayList<>(), 0);
        }

        // Filters
        List<Predicate> predicates = getInventoryItemsSearchConditions(builder, root, warehouseId, searchQuery);

        query.where(builder.and(predicates.toArray(new Predicate[0])));

        // Sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            Path<Object> sortPath = root.get(sortBy);
            Order order = ascending ? builder.asc(sortPath) : builder.desc(sortPath);
            query.orderBy(order);
        }

        // Pagination
        List<WarehouseInventoryItem> inventoryItems = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<WarehouseInventoryItem> countRoot = countQuery.from(WarehouseInventoryItem.class);

        // Apply the same predicates as the main query
        List<Predicate> countPredicates = getInventoryItemsSearchConditions(countBuilder, countRoot, warehouseId, searchQuery);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countBuilder.and(countPredicates.toArray(new Predicate[0])));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(inventoryItems, totalCount);
    }

    private List<Predicate> getInventoryItemsSearchConditions(CriteriaBuilder builder, Root<WarehouseInventoryItem> root, Integer warehouseId, String searchQuery) {
        List<Predicate> predicates = new ArrayList<>();

        // Add warehouseId filter
        predicates.add(builder.equal(root.get("warehouseId"), warehouseId));

        // Join with Component and Product and filter by their name
        Join<WarehouseInventoryItem, Component> componentJoin = root.join("component", JoinType.LEFT);
        Join<WarehouseInventoryItem, Product> productJoin = root.join("product", JoinType.LEFT);

        if (searchQuery != null && !searchQuery.isEmpty()) {
            Predicate componentPredicate = builder.like(componentJoin.get("name"), "%" + searchQuery + "%");
            Predicate productPredicate = builder.like(productJoin.get("name"), "%" + searchQuery + "%");
            predicates.add(builder.or(componentPredicate, productPredicate));
        }

        return predicates;
    }
}
