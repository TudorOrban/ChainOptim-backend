package org.chainoptim.core.user.service;

import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.core.tenant.user.repository.UserRepository;
import org.chainoptim.core.tenant.user.service.UserUpdateService;
import org.chainoptim.core.tenant.user.service.UserWriteService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserUpdateServiceTest {

    @Autowired
    private UserUpdateService userUpdateService;

    @Autowired
    private UserWriteService userWriteService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void whenRegisterNewUser_thenSaveUser() {
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

        User savedUser = userWriteService.registerNewUser(username, password, email);

        // then
        assertEquals(username, savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPasswordHash());
        assertEquals(email, savedUser.getEmail());
    }

    @Test
    void whenDeleteUser_thenRepositoryDeleteCalled() {
        // given
        String userId = "user-123";

        doNothing().when(userRepository).deleteById(userId);

        userWriteService.deleteUser(userId);

        // then
        verify(userRepository, times(1)).deleteById(userId);
    }
}
