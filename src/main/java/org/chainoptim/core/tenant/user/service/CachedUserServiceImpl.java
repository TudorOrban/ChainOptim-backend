package org.chainoptim.core.tenant.user.service;

import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.core.tenant.user.model.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * Service that provides a cached version of the AuthenticationService and UserService
 * Used to cache UserDetailsImpl objects in Redis
 * as User Custom Role permissions json is needed on every request and heavy to fetch
 */
@Service
public class CachedUserServiceImpl implements CachedUserService {

    private final AuthenticationService authenticationService;
    private final UserUpdateService userUpdateService;

    @Autowired
    public CachedUserServiceImpl(AuthenticationService authenticationService,
                                 UserUpdateService userUpdateService) {
        this.authenticationService = authenticationService;
        this.userUpdateService = userUpdateService;
    }

    @Cacheable(value = "userDetailsCache", key="#username")
    public UserDetailsImpl cachedLoadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationService.loadUserByUsername(username);
    }

    @CacheEvict(value = "userDetailsCache", key = "#result.username")
    public User assignBasicRoleToUser(String userId, User.Role role) {
        return userUpdateService.assignBasicRoleToUser(userId, role);
    }

    @CacheEvict(value = "userDetailsCache", key = "#result.username")
    public User assignCustomRoleToUser(String userId, Integer roleId) {
        return userUpdateService.assignCustomRoleToUser(userId, roleId);
    }
}
