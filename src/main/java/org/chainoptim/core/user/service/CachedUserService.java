package org.chainoptim.core.user.service;

import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.model.UserDetailsImpl;

public interface CachedUserService {

    UserDetailsImpl cachedLoadUserByUsername(String username);
    User assignBasicRoleToUser(String userId, User.Role role);
    User assignCustomRoleToUser(String userId, Integer roleId);
}
