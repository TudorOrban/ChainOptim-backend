package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.Product;
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
    public List<Product> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
        Root<Product> product = criteriaQuery.from(Product.class);

        // Add conditions
        // Filter by organizationId
        Predicate conditions = builder.conjunction();
        conditions = builder.and(conditions, builder.equal(product.get("organizationId"), organizationId));

        // Filter by search query
        conditions = builder.and(conditions, builder.like(product.get("name"), "%" + searchQuery + "%"));
        criteriaQuery.where(conditions);

        // Add sorting
        if (ascending) {
            criteriaQuery.orderBy(builder.asc(product.get(sortBy)));
        } else {
            criteriaQuery.orderBy(builder.desc(product.get(sortBy)));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
