package org.chainoptim.features.productpipeline.repository;

import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.repository.StagesSearchRepository;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class StagesSearchRepositoryImpl implements StagesSearchRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResults<Stage> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Stage> query = builder.createQuery(Stage.class);
        Root<Stage> stage = query.from(Stage.class);

        // Add conditions (organizationId and searchQuery)
        Predicate conditions = getConditions(builder, stage, organizationId, searchQuery);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(stage.get(sortBy)));
        } else {
            query.orderBy(builder.desc(stage.get(sortBy)));
        }

        // Create query with pagination
        List<Stage> stages = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<Stage> countRoot = countQuery.from(Stage.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, organizationId, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(stages, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<Stage> root, Integer organizationId, String searchQuery) {
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
