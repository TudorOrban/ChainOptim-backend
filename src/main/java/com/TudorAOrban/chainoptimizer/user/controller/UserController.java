package com.TudorAOrban.chainoptimizer.user.controller;

import com.TudorAOrban.chainoptimizer.organization.model.Organization;
import com.TudorAOrban.chainoptimizer.user.model.User;
import com.TudorAOrban.chainoptimizer.user.service.UserService;
import com.TudorAOrban.chainoptimizer.user.utils.JwtAuthenticationResponse;
import com.TudorAOrban.chainoptimizer.user.utils.JwtTokenProvider;
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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        User registeredUser = userService.registerNewUser(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getEmail());

        // Automatically log in user after registration and return JWT
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        registeredUser.getUsername(),
                        registeredUser.getPasswordHash(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                ),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                );

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    // DTO class for registration
    @Data
    static class UserRegistrationDto {
        private String username;
        private String password;
        private String email;
    }

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
