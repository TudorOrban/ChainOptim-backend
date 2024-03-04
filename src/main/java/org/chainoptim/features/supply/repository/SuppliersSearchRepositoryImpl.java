package org.chainoptim.features.supply.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.chainoptim.features.supply.model.Supplier;

import java.util.List;

public class SuppliersSearchRepositoryImpl implements SuppliersSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Supplier> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Supplier> criteriaQuery = builder.createQuery(Supplier.class);
        Root<Supplier> supplier = criteriaQuery.from(Supplier.class);

        // Add conditions
        // Filter by organizationId
        Predicate conditions = builder.conjunction();
        conditions = builder.and(conditions, builder.equal(supplier.get("organizationId"), organizationId));

        // Filter by search query
        conditions = builder.and(conditions, builder.like(supplier.get("name"), "%" + searchQuery + "%"));
        criteriaQuery.where(conditions);

        // Add sorting
        if (ascending) {
            criteriaQuery.orderBy(builder.asc(supplier.get(sortBy)));
        } else {
            criteriaQuery.orderBy(builder.desc(supplier.get(sortBy)));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
