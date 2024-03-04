package org.chainoptim.features.warehouse.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.chainoptim.features.warehouse.model.Warehouse;

import java.util.List;

public class WarehousesSearchRepositoryImpl implements WarehousesSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Warehouse> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Warehouse> criteriaQuery = builder.createQuery(Warehouse.class);
        Root<Warehouse> warehouse = criteriaQuery.from(Warehouse.class);

        // Add conditions
        // Filter by organizationId
        Predicate conditions = builder.conjunction();
        conditions = builder.and(conditions, builder.equal(warehouse.get("organizationId"), organizationId));

        // Filter by search query
        conditions = builder.and(conditions, builder.like(warehouse.get("name"), "%" + searchQuery + "%"));
        criteriaQuery.where(conditions);

        // Add sorting
        if (ascending) {
            criteriaQuery.orderBy(builder.asc(warehouse.get(sortBy)));
        } else {
            criteriaQuery.orderBy(builder.desc(warehouse.get(sortBy)));
        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
