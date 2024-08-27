package org.chainoptim.core.user.repository;

import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.core.tenant.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void whenFindByUsername_thenReturnUser() {
        // given
        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setEmail("test@example.com");
        newUser.setPasswordHash("hashedPassword");
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setRole(User.Role.NONE);

        entityManager.persist(newUser);
        entityManager.flush();

        Optional<User> foundUser = userRepository.findByUsername(newUser.getUsername());

        assertTrue(foundUser.isPresent());
        assertEquals(newUser.getUsername(), foundUser.get().getUsername());
    }

    @Test
    void whenFindByUsername_withNoUser_thenReturnEmpty() {
        Optional<User> foundUser = userRepository.findByUsername("nonExistingUser");

        assertFalse(foundUser.isPresent());
    }

}