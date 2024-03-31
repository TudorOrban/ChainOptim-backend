package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.dto.AddNotificationDTO;
import org.chainoptim.core.notifications.dto.NotificationDTOMapper;
import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.core.notifications.websocket.WebSocketMessagingService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final WebSocketMessagingService messagingService;
    private final NotificationFormatterService notificationFormatterService;
    private final NotificationPersistenceService notificationPersistenceService;
    private final NotificationDistributionService notificationDistributionService;
    private final ObjectMapper objectMapper;

    @Autowired
    public NotificationServiceImpl(WebSocketMessagingService messagingService,
                                   NotificationFormatterService notificationFormatterService,
                                   NotificationPersistenceService notificationPersistenceService,
                                   NotificationDistributionService notificationDistributionService,
                                   ObjectMapper objectMapper) {
        this.messagingService = messagingService;
        this.notificationFormatterService = notificationFormatterService;
        this.notificationPersistenceService = notificationPersistenceService;
        this.notificationDistributionService = notificationDistributionService;
        this.objectMapper = objectMapper;
    }

    public <T> void processEvent(T event, String entityType) {
        System.out.println("Processing event: " + event);

        Notification notification = notificationFormatterService.formatEvent(event, entityType);

        List<String> userIds = notificationDistributionService.distributeEventToUsers(event, entityType);

        // Send the notification to active session users
        try {
            String serializedNotification = objectMapper.writeValueAsString(notification);

            // Send the event to all users
            for (String userId : userIds) {
                if (messagingService.getSessions().containsKey(userId)) {
                    messagingService.sendMessageToUser(userId, serializedNotification);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Persist the notification in the database
        AddNotificationDTO notificationDTO = NotificationDTOMapper.mapNotificationToAddNotificationDTO(notification);
        notificationDTO.setUserIds(userIds);
        notificationPersistenceService.addNotification(notificationDTO);
    }
}
