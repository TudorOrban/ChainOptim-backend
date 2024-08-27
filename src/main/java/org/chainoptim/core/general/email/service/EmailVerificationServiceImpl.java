package org.chainoptim.core.general.email.service;

import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.core.tenant.user.repository.UserRepository;
import org.chainoptim.exception.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmailVerificationServiceImpl(EmailService emailService,
                                        UserRepository userRepository,
                                        PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void prepareUserForVerification(User newUser, boolean isFirstConfirmationEmail) {
        String token = generateVerificationToken();
        newUser.setVerificationToken(token);
        newUser.setVerificationTokenExpirationDate(calculateExpirationDate(24));
        newUser.setIsFirstConfirmationEmail(isFirstConfirmationEmail);
        newUser.setEnabled(false);
    }

    public void sendConfirmationMail(String email, String token, boolean isInOrganization) {
        String confirmationUrl = "http://localhost:8080/api/v1/verify-email" +
            "?token=" + token +
            (isInOrganization ? "?isInOrganization=true" : "");

        String emailMessage = !isInOrganization ?
                "Your email has been used to register an account with us at ChainOptim. " +
                "Please click the following link to confirm your email: " :

                "A user of our software ChainOptim has created an account for you " +
                "and invited you to join their organization. " +
                "Please click the following link to confirm your email and set your password: ";

        emailMessage += confirmationUrl;

        emailService.sendEmail(email, "Confirm your email", emailMessage);
    }

    public String verifyAccountEmail(String token, boolean isInOrganization, String newPassword) {
        try {
            User user = userRepository.findByVerificationToken(token)
                    .orElseThrow(() -> new ValidationException("Invalid token"));
            if (user.getVerificationTokenExpirationDate().isBefore(LocalDateTime.now())) {
                throw new ValidationException("Token expired");
            }

            user.setEnabled(true);
            user.setVerificationToken(null);
            user.setVerificationTokenExpirationDate(null);

            if (isInOrganization && newPassword != null) {
                user.setPasswordHash(passwordEncoder.encode(newPassword));
            }

            userRepository.save(user);

            return "Email successfully verified.";
        } catch (Exception e) {
            return "Error verifying email: " + e.getMessage();
        }
    }

    // Background job to resend confirmation tokens or clean up
//    @Scheduled(cron = "0 0 0 * * ?")
    public void checkTokenExpirations() {
        List<User> usersWithExpiredTokens = userRepository.findByTokenExpirationDateBefore(LocalDateTime.now());

        for (User user : usersWithExpiredTokens) {
            if (user.getOrganization() != null) continue; // Skip users created by organizations

            if (user.getIsFirstConfirmationEmail()) {
                refreshVerificationToken(user);
            } else {
                userRepository.delete(user);
            }
        }
    }

    private void refreshVerificationToken(User user) {
        prepareUserForVerification(user, false);

        userRepository.save(user);

        resendConfirmationMail(user.getEmail(), user.getVerificationToken());
    }

    private void resendConfirmationMail(String email, String token) {
        String confirmationUrl = "http://localhost:8080/api/v1/verify-email" +
                "?token=" + token;

        String emailMessage = "Your email has been used to register an account with us at ChainOptim. " +
                "Please click the following link to confirm your email. " +
                "Otherwise, your account will be deleted in 24 hours. ";

        emailMessage += confirmationUrl;

        emailService.sendEmail(email, "Confirm your email", emailMessage);
    }

    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    private LocalDateTime calculateExpirationDate(int hours) {
        return LocalDateTime.now().plusHours(hours);
    }
}
