package org.chainoptim.core.user.service;

import org.chainoptim.core.user.model.UserDetailsImpl;

public interface CachedUserService {

    UserDetailsImpl cachedLoadUserByUsername(String username);
}
