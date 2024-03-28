package org.chainoptim.core.user.service;

import org.chainoptim.core.user.model.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * Service that provides a cached version of the AuthenticationService
 * Used to cache UserDetailsImpl objects in Redis
 * as User Custom Role permissions json is needed on every request and heavy to fetch
 */
@Service
public class CachedUserServiceImpl implements CachedUserService {

    private final AuthenticationService authenticationService;

    @Autowired
    public CachedUserServiceImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Cacheable(value = "userDetailsCache", key="#username")
    public UserDetailsImpl cachedLoadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationService.loadUserByUsername(username);
    }
}
