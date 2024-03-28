package org.chainoptim.core.user.service;

import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrganizationRepository organizationRepository;


    @Test
    void whenGetUserById_thenReturnUser() {
        // given
        String userId = "user-123";
        User mockUser = new User();
        mockUser.setId(userId);

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User foundUser = userService.getUserById(userId);

        // then
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void whenGetUserById_withNonExistingId_thenReturnNull() {
        // given
        String userId = "user-123";

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User foundUser = userService.getUserById(userId);

        // then
        assertNull(foundUser);
    }

    @Test
    void whenGetUserByUsername_thenReturnUser() {
        // given
        String username = "testuser";
        User mockUser = new User();
        mockUser.setUsername(username);

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        Optional<User> foundUser = userService.getUserByUsername(username);

        // then
        assertTrue(foundUser.isPresent());
        assertEquals(username, foundUser.get().getUsername());
    }

    @Test
    void whenGetUserByUsername_withNonExistingUsername_thenReturnEmpty() {
        // given
        String username = "nonExistingUser";

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserByUsername(username);

        // then
        assertFalse(foundUser.isPresent());
    }


}