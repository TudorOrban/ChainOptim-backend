package org.chainoptim.core.tenant.user.repository;

import org.chainoptim.core.tenant.organization.model.Organization;
import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.shared.search.model.PaginatedResults;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.List;

public class UserSearchRepositoryImpl implements UserSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResults<User> searchPublicUsers(String searchQuery, Integer page, Integer pageSize) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> user = query.from(User.class);

        // Add conditions (searchQuery, no organization, isProfilePublic)
        Predicate conditions = getConditions(builder, user, searchQuery);
        query.where(conditions);

        // Create query with pagination
        List<User> users = entityManager.createQuery(query)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        // Query total results count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(getConditions(countBuilder, countRoot, searchQuery));

        // Execute count query
        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(users, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<User> root, String searchQuery) {
        Predicate conditions = builder.conjunction();

        // Search by username
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("username"), "%" + searchQuery + "%"));
        }

        // Ensure the organization is null (no organization)
        Join<User, Organization> organizationJoin = root.join("organization", JoinType.LEFT);
        conditions = builder.and(conditions, builder.isNull(organizationJoin.get("id")));

        // Check if the profile is public
        conditions = builder.and(conditions, builder.isTrue(root.get("isProfilePublic")));

        return conditions;
    }

}
