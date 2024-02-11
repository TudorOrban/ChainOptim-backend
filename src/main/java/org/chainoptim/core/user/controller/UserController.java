package org.chainoptim.core.user.controller;

import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.service.UserService;
import org.chainoptim.core.user.utils.JwtAuthenticationResponse;
import org.chainoptim.core.user.dto.UserSearchResultDTO;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.user.utils.JwtTokenProvider;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(id));

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDto = new UserDto();

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



    @Data
    public class UserDto {
        private String id;
        private String username;
        private String email;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Organization organization;
        private User.Role role;
    }


    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
