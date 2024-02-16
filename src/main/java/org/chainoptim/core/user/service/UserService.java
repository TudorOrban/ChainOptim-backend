package org.chainoptim.core.user.service;

import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.chainoptim.core.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User registerNewUser(String username, String password, String email);
    public User getUserById(String id);
    public Optional<User> getUserByUsername(String username);
    public List<UserSearchResultDTO> searchUsersByUsername(String username);
    public List<User> getAllUsers();
    public User updateUser(User user);
    public void deleteUser(String id);
    public User registerNewOrganizationUser(String username, String password, String email, Integer organizationId, User.Role role);

}
