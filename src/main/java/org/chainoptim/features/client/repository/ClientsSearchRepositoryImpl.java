package org.chainoptim.features.client.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.chainoptim.features.client.model.Client;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public class ClientsSearchRepositoryImpl implements ClientsSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<Client> findByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> query = builder.createQuery(Client.class);
        Root<Client> client = query.from(Client.class);

        // Add conditions (organizationId and searchQuery)
        Predicate conditions = getConditions(builder, client, organizationId, searchQuery);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(client.get(sortBy)));
        } else {
            query.orderBy(builder.desc(client.get(sortBy)));
        }

        // Create query with pagination
        List<Client> clients = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<Client> countRoot = countQuery.from(Client.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, organizationId, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(clients, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<Client> root, Integer organizationId, String searchQuery) {
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
