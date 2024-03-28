package org.chainoptim.core.user.service;

import org.chainoptim.core.user.model.UserDetailsImpl;

public interface AuthenticationService {

    UserDetailsImpl loadUserByUsername(String username);
}
