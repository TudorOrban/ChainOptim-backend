package org.chainoptim.core.notifications.service;

import org.chainoptim.config.WebSocketMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {


    private final WebSocketMessagingService messagingService;

    @Autowired
    public NotificationServiceImpl(WebSocketMessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public <T> void processEvent(T event) {
        System.out.println("Processing event: " + event);
        String message = "Message sent";
        String userId = "086e9e96-a8ef-11ee-bffa-00155de90539";
        messagingService.sendMessageToUser(userId, message);
    }
}
