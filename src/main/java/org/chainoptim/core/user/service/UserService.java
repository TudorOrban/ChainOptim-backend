package org.chainoptim.core.user.service;

import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.chainoptim.core.user.model.User;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Fetch
    User getUserById(String id);
    Optional<User> getUserByUsername(String username);
    List<UserSearchResultDTO> searchUsersByUsername(String username);
    List<UserSearchResultDTO> searchUsersByCustomRoleId(Integer roleId);
    PaginatedResults<UserSearchResultDTO> searchPublicUsers(String searchQuery, Integer page, Integer pageSize);



}
