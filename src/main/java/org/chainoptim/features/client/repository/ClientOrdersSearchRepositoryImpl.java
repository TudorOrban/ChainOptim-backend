package org.chainoptim.features.client.repository;

import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class ClientOrdersSearchRepositoryImpl implements ClientOrdersSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResults<ClientOrder> findByClientIdAdvanced(Integer clientId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientOrder> query = builder.createQuery(ClientOrder.class);
        Root<ClientOrder> supplierOrder = query.from(ClientOrder.class);

        // Add conditions (clientId and searchQuery)
        Predicate conditions = getConditions(builder, supplierOrder, clientId, searchQuery);
        query.where(conditions);

        // Add sorting
        if (ascending) {
            query.orderBy(builder.asc(supplierOrder.get(sortBy)));
        } else {
            query.orderBy(builder.desc(supplierOrder.get(sortBy)));
        }

        // Create query with pagination
        List<ClientOrder> supplierOrders = entityManager.createQuery(query)
                .setFirstResult((page - 1) * itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<ClientOrder> countRoot = countQuery.from(ClientOrder.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, clientId, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(supplierOrders, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<ClientOrder> root, Integer clientId, String searchQuery) {
        Predicate conditions = builder.conjunction();
        if (clientId != null) {
            conditions = builder.and(conditions, builder.equal(root.get("clientId"), clientId));
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("companyId"), "%" + searchQuery + "%"));
        }
        return conditions;
    }
}
