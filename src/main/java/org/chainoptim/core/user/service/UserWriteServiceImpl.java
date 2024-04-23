package org.chainoptim.core.user.service;

import org.chainoptim.core.email.service.EmailVerificationService;
import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserWriteServiceImpl implements UserWriteService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final EmailVerificationService emailVerificationService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.environment}")
    private String environment;

    @Autowired
    public UserWriteServiceImpl(UserRepository userRepository,
                                OrganizationRepository organizationRepository,
                                EmailVerificationService emailVerificationService,
                                PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.emailVerificationService = emailVerificationService;
        this.passwordEncoder = passwordEncoder;
    }

    // Create
    public User registerNewUser(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setRole(User.Role.NONE);

        // Skip email verification in dev and test environments
        if (!environment.equals("prod")) {
            return userRepository.save(newUser);
        }

        emailVerificationService.prepareUserForVerification(newUser, true);

        User registeredUser = userRepository.save(newUser);

        emailVerificationService.sendConfirmationMail(email, newUser.getVerificationToken(), false);

        return registeredUser;
    }


    public User registerNewOrganizationUser(String username, String email, Integer organizationId, User.Role role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(null);
        newUser.setEmail(email);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        // Set role, default to NONE if null
        newUser.setRole(Optional.ofNullable(role).orElse(User.Role.NONE));

        // Set organizationId if provided
        if (organizationId != null) {
            Organization organization = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            newUser.setOrganization(organization);
        }

        // Skip email verification in dev and test environments
        if (!environment.equals("prod")) {
            return userRepository.save(newUser);
        }

        emailVerificationService.prepareUserForVerification(newUser, true);

        User registeredUser = userRepository.save(newUser);

        emailVerificationService.sendConfirmationMail(email, newUser.getVerificationToken(), true);

        return registeredUser;
    }

    // Delete
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
