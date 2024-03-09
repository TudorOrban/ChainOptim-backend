package org.chainoptim.features.factory.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.ArrayList;
import java.util.List;

public class FactoryInventorySearchRepositoryImpl implements FactoryInventorySearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<FactoryInventoryItem> findFactoryItemsById(
            Integer factoryId,
            String searchQuery,
            String sortBy,
            boolean ascending,
            int page,
            int itemsPerPage
    ) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FactoryInventoryItem> query = builder.createQuery(FactoryInventoryItem.class);
        Root<FactoryInventoryItem> root = query.from(FactoryInventoryItem.class);

        // Skip if no factoryId (add error in the future)
        if (factoryId == null) {
            return new PaginatedResults<>(new ArrayList<>(), 0);
        }

        // Filters
        List<Predicate> predicates = getInventoryItemsSearchConditions(builder, root, factoryId, searchQuery);

        query.where(builder.and(predicates.toArray(new Predicate[0])));

        // Sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            Path<Object> sortPath = root.get(sortBy);
            Order order = ascending ? builder.asc(sortPath) : builder.desc(sortPath);
            query.orderBy(order);
        }

        // Pagination
        List<FactoryInventoryItem> inventoryItems = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<FactoryInventoryItem> countRoot = countQuery.from(FactoryInventoryItem.class);

        // Apply the same predicates as the main query
        List<Predicate> countPredicates = getInventoryItemsSearchConditions(countBuilder, countRoot, factoryId, searchQuery);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countBuilder.and(countPredicates.toArray(new Predicate[0])));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(inventoryItems, totalCount);
    }

    private List<Predicate> getInventoryItemsSearchConditions(CriteriaBuilder builder, Root<FactoryInventoryItem> root, Integer factoryId, String searchQuery) {
        List<Predicate> predicates = new ArrayList<>();

        // Add factoryId filter
        predicates.add(builder.equal(root.get("factoryId"), factoryId));

        // Join with Component and Product and filter by their name
        Join<FactoryInventoryItem, Component> componentJoin = root.join("component", JoinType.LEFT);
        Join<FactoryInventoryItem, Product> productJoin = root.join("product", JoinType.LEFT);

        if (searchQuery != null && !searchQuery.isEmpty()) {
            Predicate componentPredicate = builder.like(componentJoin.get("name"), "%" + searchQuery + "%");
            Predicate productPredicate = builder.like(productJoin.get("name"), "%" + searchQuery + "%");
            predicates.add(builder.or(componentPredicate, productPredicate));
        }

        return predicates;
    }
}
