package com.TudorAOrban.chainoptimizer.user.service;

import com.TudorAOrban.chainoptimizer.dto.UserSearchResultDTO;
import com.TudorAOrban.chainoptimizer.organization.model.Organization;
import com.TudorAOrban.chainoptimizer.organization.repository.OrganizationRepository;
import com.TudorAOrban.chainoptimizer.user.model.User;
import com.TudorAOrban.chainoptimizer.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setRole(User.Role.NONE);

        return userRepository.save(newUser);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username).orElse(null));
    }

    public List<UserSearchResultDTO> searchUsersByUsername(String username) {
        // This is a simplified version. Adjust the method to query users based on your requirements
        return userRepository.findByUsernameContaining(username).stream()
                .map(user -> new UserSearchResultDTO(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public User registerNewOrganizationUser(String username, String password, String email, Integer organizationId, User.Role role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        // Set role, default to NONE if null
        newUser.setRole(Optional.ofNullable(role).orElse(User.Role.NONE));

        // Set organizationId if provided
        if (organizationId != null) {
            // Check if organization exists
            Organization organization = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            newUser.setOrganization(organization);
        }

        return userRepository.save(newUser);
    }
}
