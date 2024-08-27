package org.chainoptim.core.general.email.service;

public interface EmailService {

    void sendEmail(String to, String subject, String text);
}
