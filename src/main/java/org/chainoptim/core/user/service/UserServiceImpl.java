package org.chainoptim.core.user.service;

import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final OrganizationRepository organizationRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OrganizationRepository organizationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        return userRepository.findByUsername(username);
    }

    public List<UserSearchResultDTO> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContaining(username).stream()
                .map(user -> new UserSearchResultDTO(user.getId(), user.getUsername(), user.getEmail()))
                .toList();
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
            Organization organization = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            newUser.setOrganization(organization);
        }

        return userRepository.save(newUser);
    }
}
