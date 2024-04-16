package org.chainoptim.core.email.service;

import org.chainoptim.core.user.model.User;

public interface EmailVerificationService {

    void prepareUserForVerification(User newUser);
    void sendConfirmationMail(String email, String token);
    String verifyAccountEmail(String token);
}
