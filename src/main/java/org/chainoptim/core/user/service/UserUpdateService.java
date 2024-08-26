package org.chainoptim.core.user.service;

import org.chainoptim.core.user.dto.UpdateUserProfileDTO;
import org.chainoptim.core.user.model.User;

public interface UserUpdateService {

    // Update
    User updateUser(UpdateUserProfileDTO userDTO);
    User assignBasicRoleToUser(String userId, User.Role role);
    User assignCustomRoleToUser(String userId, Integer roleId);
    User removeUserFromOrganization(String userId, Integer organizationId);

}
