package org.chainoptim.features.factory.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.chainoptim.features.factory.model.Factory;

import java.util.List;

public class FactoriesSearchRepositoryImpl implements FactoriesSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Factory> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Factory> criteriaQuery = builder.createQuery(Factory.class);
        Root<Factory> factory = criteriaQuery.from(Factory.class);

        // Add conditions
        // Filter by organizationId
        Predicate conditions = builder.conjunction();
        conditions = builder.and(conditions, builder.equal(factory.get("organizationId"), organizationId));

        // Filter by search query
        conditions = builder.and(conditions, builder.like(factory.get("name"), "%" + searchQuery + "%"));
        criteriaQuery.where(conditions);

        // Add sorting
        if (ascending) {
            criteriaQuery.orderBy(builder.asc(factory.get(sortBy)));
        } else {
            criteriaQuery.orderBy(builder.desc(factory.get(sortBy)));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
