package org.chainoptim.core.tenant.user.service;

import org.chainoptim.core.tenant.user.dto.UpdateUserProfileDTO;
import org.chainoptim.core.tenant.user.model.User;

public interface UserUpdateService {

    // Update
    User updateUser(UpdateUserProfileDTO userDTO);
    User assignBasicRoleToUser(String userId, User.Role role);
    User assignCustomRoleToUser(String userId, Integer roleId);
    User removeUserFromOrganization(String userId, Integer organizationId);

}
