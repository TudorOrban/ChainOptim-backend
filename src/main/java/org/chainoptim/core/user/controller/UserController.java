package org.chainoptim.core.user.controller;

import org.chainoptim.core.user.dto.AssignRoleDTO;
import org.chainoptim.core.user.dto.UserWithOrganizationDTO;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.service.UserService;
import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users") // TODO: Secure all these endpoints
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    // Update
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PutMapping("/{userId}/assign-role")
    public ResponseEntity<User> assignCustomRoleToUser(@PathVariable("userId") String userId, @RequestBody AssignRoleDTO roleDTO) {
        return ResponseEntity.ok(userService.assignCustomRoleToUser(userId, roleDTO.getRoleId()));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
