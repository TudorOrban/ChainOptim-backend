package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientShipment;
import org.chainoptim.shared.search.model.PaginatedResults;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class ClientShipmentsSearchRepositoryImpl implements ClientShipmentsSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<ClientShipment> findByClientOrderIdAdvanced(Integer clientOrderId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientShipment> query = builder.createQuery(ClientShipment.class);
        Root<ClientShipment> clientShipment = query.from(ClientShipment.class);

        // Add conditions (clientId and searchQuery)
        Predicate conditions = getConditions(builder, clientShipment, clientOrderId, searchQuery);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(clientShipment.get(sortBy)));
        } else {
            query.orderBy(builder.desc(clientShipment.get(sortBy)));
        }

        // Create query with pagination
        List<ClientShipment> clientShipments = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<ClientShipment> countRoot = countQuery.from(ClientShipment.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, clientOrderId, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(clientShipments, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<ClientShipment> root, Integer clientOrderId, String searchQuery) {
        Predicate conditions = builder.conjunction();
        if (clientOrderId != null) {
            conditions = builder.and(conditions, builder.equal(root.get("clientOrderId"), clientOrderId));
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("clientOrderId"), "%" + searchQuery + "%"));
        }
        return conditions;
    }
}
