package org.chainoptim.core.email.service;

public interface EmailService {

    void sendEmail(String to, String subject, String text);
}
