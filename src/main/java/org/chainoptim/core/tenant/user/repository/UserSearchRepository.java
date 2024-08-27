package org.chainoptim.core.tenant.user.repository;

import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface UserSearchRepository {

    PaginatedResults<User> searchPublicUsers(String searchQuery, Integer page, Integer pageSize);
}
