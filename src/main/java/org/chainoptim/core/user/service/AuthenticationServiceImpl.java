package org.chainoptim.core.user.service;

import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.model.UserDetailsImpl;
import org.chainoptim.core.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsImpl loadUserByUsername(String username) {
        logger.info("Attempting to load user by username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPasswordHash());
        userDetails.setOrganizationId(user.getOrganization().getId());
        userDetails.setRole(user.getRole());
        userDetails.setCustomRole(user.getCustomRole());
        userDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        return userDetails;
    }
}
