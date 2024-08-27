package org.chainoptim.core.tenant.user.service;

import org.chainoptim.core.tenant.user.model.UserDetailsImpl;

public interface AuthenticationService {

    UserDetailsImpl loadUserByUsername(String username);
}
