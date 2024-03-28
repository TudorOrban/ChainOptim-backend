package org.chainoptim.core.user.repository;

import org.chainoptim.core.user.model.User;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface UserSearchRepository {

    PaginatedResults<User> searchPublicUsers(String searchQuery, Integer page, Integer pageSize);
}
