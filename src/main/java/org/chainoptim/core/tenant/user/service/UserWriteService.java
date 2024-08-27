package org.chainoptim.core.tenant.user.service;

import org.chainoptim.core.tenant.user.model.User;

public interface UserWriteService {

    User registerNewUser(String username, String password, String email);
    User registerNewOrganizationUser(String username, String email, Integer organizationId, User.Role role);

    // Delete
    void deleteUser(String id);

}
