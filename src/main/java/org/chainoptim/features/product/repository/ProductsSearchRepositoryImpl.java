package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.Product;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductsSearchRepositoryImpl implements ProductsSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<Product> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        // Add conditions (organizationId and searchQuery)
        Predicate conditions = getConditions(builder, product, organizationId, searchQuery);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(product.get(sortBy)));
        } else {
            query.orderBy(builder.desc(product.get(sortBy)));
        }

        // Create query with pagination
        List<Product> products = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, organizationId, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<Product>(products, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<Product> root, Integer organizationId, String searchQuery) {
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
