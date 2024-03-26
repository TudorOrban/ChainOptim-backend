package org.chainoptim.core.user.service;

import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.chainoptim.core.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User registerNewUser(String username, String password, String email);
    User getUserById(String id);
    Optional<User> getUserByUsername(String username);
    List<UserSearchResultDTO> searchUsersByUsername(String username);
    List<UserSearchResultDTO> searchUsersByCustomRoleId(Integer roleId);
    User updateUser(User user);
    void deleteUser(String id);
    User registerNewOrganizationUser(String username, String password, String email, Integer organizationId, User.Role role);

}
