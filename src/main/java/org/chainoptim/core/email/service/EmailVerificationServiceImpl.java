package org.chainoptim.core.email.service;

import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailService emailService;
    private final UserRepository userRepository;

    @Autowired
    public EmailVerificationServiceImpl(EmailService emailService,
                                         UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public void prepareUserForVerification(User newUser) {
        String token = generateVerificationToken();
        newUser.setVerificationToken(token);
        newUser.setVerificationTokenExpirationDate(calculateExpirationDate(24));
        newUser.setEnabled(false);
    }

    public void sendConfirmationMail(String email, String token) {
        String confirmationUrl = "http://localhost:8080/api/v1/verify-email?token=" + token;
        emailService.sendEmail(
                email,
                "Confirm your email",
                "Your email has been used to register an account with us at ChainOptim. " +
                "Please click the following link to confirm your email: " + confirmationUrl
        );
    }

    public String verifyAccountEmail(String token) {
        try {
            User user = userRepository.findByVerificationToken(token)
                    .orElseThrow(() -> new ValidationException("Invalid token"));
            if (user.getVerificationTokenExpirationDate().isBefore(LocalDateTime.now())) {
                throw new ValidationException("Token expired");
            }

            user.setEnabled(true);
            user.setVerificationToken(null);
            user.setVerificationTokenExpirationDate(null);

            userRepository.save(user);

            return "Email successfully verified.";
        } catch (Exception e) {
            return "Error verifying email: " + e.getMessage();
        }
    }

    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    private LocalDateTime calculateExpirationDate(int hours) {
        return LocalDateTime.now().plusHours(hours);
    }
}
