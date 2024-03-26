package org.chainoptim.core.user.service;

import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.chainoptim.core.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Fetch
    User getUserById(String id);
    Optional<User> getUserByUsername(String username);
    List<UserSearchResultDTO> searchUsersByUsername(String username);
    List<UserSearchResultDTO> searchUsersByCustomRoleId(Integer roleId);

    /// Create
    User registerNewUser(String username, String password, String email);
    User registerNewOrganizationUser(String username, String password, String email, Integer organizationId, User.Role role);

    // Update
    User updateUser(User user);
    User assignCustomRoleToUser(String userId, Integer roleId);

    // Delete
    void deleteUser(String id);

}
