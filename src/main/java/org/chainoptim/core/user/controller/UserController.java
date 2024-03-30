package org.chainoptim.core.user.controller;

import org.chainoptim.core.redis.service.RedisService;
import org.chainoptim.core.user.dto.AssignBasicRoleDTO;
import org.chainoptim.core.user.dto.AssignCustomRoleDTO;
import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.chainoptim.core.user.dto.UserWithOrganizationDTO;
import org.chainoptim.core.user.service.*;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.core.user.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users") // TODO: Secure all these endpoints
public class UserController {

    private final UserService userService;
    private final UserUpdateService userUpdateService;
    private final UserWriteService userWriteService;
    private final CachedUserService cachedUserService;

    private final RedisService redisService;

    @Autowired
    public UserController(UserService userService,
                          UserWriteService userWriteService,
                          UserUpdateService userUpdateService,
                          CachedUserService cachedUserService,
                          RedisService redisService) {
        this.userService = userService;
        this.userWriteService = userWriteService;
        this.userUpdateService = userUpdateService;
        this.cachedUserService = cachedUserService;
        this.redisService = redisService;
    }

    // Fetch
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(id));

        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserWithOrganizationDTO> getUserByUsername(@PathVariable String username) {
        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserWithOrganizationDTO userDto = new UserWithOrganizationDTO();

            // Map all fields from User to UserDto
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setCreatedAt(user.getCreatedAt());
            userDto.setUpdatedAt(user.getUpdatedAt());
            userDto.setOrganization(user.getOrganization());
            userDto.setRole(user.getRole());

            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserSearchResultDTO>> searchUsers(@PathVariable String username) {
        List<UserSearchResultDTO> users = userService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/custom-role/{roleId}")
    public ResponseEntity<List<UserSearchResultDTO>> searchUsersByCustomRoleId(@PathVariable Integer roleId) {
        List<UserSearchResultDTO> users = userService.searchUsersByCustomRoleId(roleId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/public")
    public ResponseEntity<PaginatedResults<UserSearchResultDTO>> searchPublicUsers(
            @RequestParam(defaultValue = "") String searchQuery,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer itemsPerPage) {
        PaginatedResults<UserSearchResultDTO> users = userService.searchPublicUsers(searchQuery, page, itemsPerPage);
        return ResponseEntity.ok(users);
    }

    // Update
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userUpdateService.updateUser(user));
    }

    @PutMapping("/{userId}/assign-basic-role")
    public ResponseEntity<User> assignBasicRoleToUser(@PathVariable("userId") String userId, @RequestBody AssignBasicRoleDTO roleDTO) {
        if (redisService.isRedisAvailable()) {
            System.out.println("Redis is available");
            return ResponseEntity.ok(cachedUserService.assignBasicRoleToUser(userId, roleDTO.getRole()));
        } else {
            System.out.println("Redis is not available");
            return ResponseEntity.ok(userUpdateService.assignBasicRoleToUser(userId, roleDTO.getRole()));
        }
    }

    @PutMapping("/{userId}/assign-custom-role")
    public ResponseEntity<User> assignCustomRoleToUser(@PathVariable("userId") String userId, @RequestBody AssignCustomRoleDTO roleDTO) {
        if (redisService.isRedisAvailable()) {
            return ResponseEntity.ok(cachedUserService.assignCustomRoleToUser(userId, roleDTO.getRoleId()));
        } else {
            return ResponseEntity.ok(userUpdateService.assignCustomRoleToUser(userId, roleDTO.getRoleId()));
        }
    }

    @PutMapping("/{userId}/remove-from-organization/{organizationId}")
    public ResponseEntity<User> removeUserFromOrganization(@PathVariable("userId") String userId, @PathVariable("organizationId") Integer organizationId) {
        return ResponseEntity.ok(userUpdateService.removeUserFromOrganization(userId, organizationId));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userWriteService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
