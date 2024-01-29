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

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenRegisterNewUser_thenSaveUser() {
        // given
        String username = "testuser";
        String password = "testpassword";
        String email = "newuser@example.com";

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash("encodedPassword");
        user.setEmail(email);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.registerNewUser(username, password, email);

        // then
        assertEquals(username, savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPasswordHash());
        assertEquals(email, savedUser.getEmail());
    }

    @Test
    public void whenGetUserById_thenReturnUser() {
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
    public void whenGetUserById_withNonExistingId_thenReturnNull() {
        // given
        String userId = "user-123";

        // when
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User foundUser = userService.getUserById(userId);

        // then
        assertNull(foundUser);
    }

    @Test
    public void whenGetUserByUsername_thenReturnUser() {
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
    public void whenGetUserByUsername_withNonExistingUsername_thenReturnEmpty() {
        // given
        String username = "nonExistingUser";

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserByUsername(username);

        // then
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void whenDeleteUser_thenRepositoryDeleteCalled() {
        // given
        String userId = "user-123";

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        // then
        verify(userRepository, times(1)).deleteById(userId);
    }
}