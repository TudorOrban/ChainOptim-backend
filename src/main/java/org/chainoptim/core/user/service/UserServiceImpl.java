package org.chainoptim.core.user.service;

import org.chainoptim.core.organization.repository.CustomRoleRepository;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.user.repository.UserRepository;

import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           OrganizationRepository organizationRepository,
                            CustomRoleRepository customRoleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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

}
