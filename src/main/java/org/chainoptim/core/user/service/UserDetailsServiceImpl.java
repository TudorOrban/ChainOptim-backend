package org.chainoptim.core.user.service;

import org.chainoptim.core.user.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthenticationService authenticationService;
    private final CachedUserService cachedUserService;
    private final boolean isRedisAvailable;

    @Autowired
    public UserDetailsServiceImpl(AuthenticationService authenticationService,
                                    CachedUserService cachedUserService,
                                  boolean isRedisAvailable) {
        this.authenticationService = authenticationService;
        this.cachedUserService = cachedUserService;
        this.isRedisAvailable = isRedisAvailable;
    }

    /*
     * Service method used on every endpoint call to load user details into SecurityContext
     * Uses a cached version of the AuthenticationService if Redis is available
     */
    @Override
    public UserDetailsImpl loadUserByUsername(String username) {
        if (isRedisAvailable) {
            System.out.println("Redis is Available");
            return cachedUserService.cachedLoadUserByUsername(username);
        } else {
            System.out.println("Redis is Not Available");
            return authenticationService.loadUserByUsername(username);
        }
    }
}
