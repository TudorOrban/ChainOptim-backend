package org.chainoptim.core.tenant.user.service;

import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.core.tenant.user.model.UserDetailsImpl;

public interface CachedUserService {

    UserDetailsImpl cachedLoadUserByUsername(String username);
    User assignBasicRoleToUser(String userId, User.Role role);
    User assignCustomRoleToUser(String userId, Integer roleId);
}
