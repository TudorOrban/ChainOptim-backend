package org.chainoptim.core.general.email.service;

import org.chainoptim.core.tenant.user.model.User;

public interface EmailVerificationService {

    void prepareUserForVerification(User newUser, boolean isFirstConfirmationEmail);
    void sendConfirmationMail(String email, String token, boolean isInOrganization);
    String verifyAccountEmail(String token, boolean isInOrganization, String newPassword);
}
