package org.chainoptim.core.tenant.user.service;

import org.chainoptim.core.tenant.customrole.model.CustomRole;
import org.chainoptim.core.tenant.customrole.repository.CustomRoleRepository;
import org.chainoptim.core.tenant.user.dto.UpdateUserProfileDTO;
import org.chainoptim.core.tenant.user.dto.UserDTOMapper;
import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.core.tenant.user.repository.UserRepository;

import org.chainoptim.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUpdateServiceImpl implements UserUpdateService {

    private final UserRepository userRepository;
    private final CustomRoleRepository customRoleRepository;

    @Autowired
    public UserUpdateServiceImpl(UserRepository userRepository,
                                 CustomRoleRepository customRoleRepository) {
        this.userRepository = userRepository;
        this.customRoleRepository = customRoleRepository;
    }

    // Update
    public User updateUser(UpdateUserProfileDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userDTO.getId() + " not found"));

        User updatedUser = UserDTOMapper.setUpdateUserProfileDTOToUser(userDTO, user);

        return userRepository.save(updatedUser);
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


}
