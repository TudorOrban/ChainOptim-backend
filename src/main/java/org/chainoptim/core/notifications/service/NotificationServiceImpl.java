package org.chainoptim.core.notifications.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    public <T> void processEvent(T event) {
        System.out.println("Processing event: " + event);
    }
}
