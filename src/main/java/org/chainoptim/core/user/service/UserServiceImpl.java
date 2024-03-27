package org.chainoptim.core.user.service;

import org.chainoptim.core.organization.model.CustomRole;
import org.chainoptim.core.organization.repository.CustomRoleRepository;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.user.repository.UserRepository;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.shared.search.model.PaginatedResults;
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
    private final CustomRoleRepository customRoleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           OrganizationRepository organizationRepository,
                            CustomRoleRepository customRoleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.customRoleRepository = customRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Fetch
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

    public List<UserSearchResultDTO> searchUsersByCustomRoleId(Integer roleId) {
        return userRepository.findByCustomRoleId(roleId).stream()
                .map(user -> new UserSearchResultDTO(user.getId(), user.getUsername(), user.getEmail()))
                .toList();
    }

    public PaginatedResults<UserSearchResultDTO> searchPublicUsers(String searchQuery, Integer page, Integer pageSize) {
        PaginatedResults<User> paginatedResults = userRepository.searchPublicUsers(searchQuery, page, pageSize);
        List<UserSearchResultDTO> mappedResults = paginatedResults.results.stream()
                .map(user -> new UserSearchResultDTO(user.getId(), user.getUsername(), user.getEmail()))
                .toList();

        return new PaginatedResults<>(mappedResults, paginatedResults.totalCount);
    }

    // Create
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

    // Update
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User assignBasicRoleToUser(String userId, User.Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));

        user.setRole(role);

        return userRepository.save(user);
    }

    public User assignCustomRoleToUser(String userId, Integer roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));

        CustomRole customRole = null;
        if (roleId != null) { // Allow null roleId
            customRole = customRoleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role with ID: " + roleId + " not found"));
        }
        user.setCustomRole(customRole);

        return userRepository.save(user);
    }

    public User removeUserFromOrganization(String userId, Integer organizationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));

        if (user.getOrganization() != null && user.getOrganization().getId().equals(organizationId)) {
            user.setOrganization(null);
            user.setRole(User.Role.NONE);
            user.setCustomRole(null);
        }

        return userRepository.save(user);
    }

    // Delete
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }


}
