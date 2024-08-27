package org.chainoptim.core.overview.notifications.service;

import org.chainoptim.core.general.email.service.EmailService;
import org.chainoptim.core.overview.notifications.dto.AddNotificationDTO;
import org.chainoptim.core.overview.notifications.dto.NotificationDTOMapper;
import org.chainoptim.core.overview.notifications.model.Notification;
import org.chainoptim.core.overview.notifications.model.NotificationUserDistribution;
import org.chainoptim.core.overview.notifications.websocket.WebSocketMessagingService;
import org.chainoptim.features.demand.model.ClientOrderEvent;
import org.chainoptim.features.supply.model.SupplierOrderEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final WebSocketMessagingService messagingService;
    private final NotificationFormatterService notificationFormatterService;
    private final NotificationPersistenceService notificationPersistenceService;
    private final NotificationDistributionService notificationDistributionService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @Value("${app.environment}")
    private String appEnvironment;

    @Autowired
    public NotificationServiceImpl(WebSocketMessagingService messagingService,
                                   NotificationFormatterService notificationFormatterService,
                                   NotificationPersistenceService notificationPersistenceService,
                                   NotificationDistributionService notificationDistributionService,
                                   EmailService emailService,
                                   ObjectMapper objectMapper) {
        this.messagingService = messagingService;
        this.notificationFormatterService = notificationFormatterService;
        this.notificationPersistenceService = notificationPersistenceService;
        this.notificationDistributionService = notificationDistributionService;
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    public void createNotification(SupplierOrderEvent event) {
        Notification notification = notificationFormatterService.formatEvent(event);

        NotificationUserDistribution users = notificationDistributionService.distributeEventToUsers(event);

        sendNotification(notification, users);
    }

    public void createNotification(ClientOrderEvent event) {
        Notification notification = notificationFormatterService.formatEvent(event);

        NotificationUserDistribution users = notificationDistributionService.distributeEventToUsers(event);

        sendNotification(notification, users);
    }

    private void sendNotification(Notification notification, NotificationUserDistribution users) {
        List<String> notificationUserIds = users.getNotificationUserIds();
        List<String> emailUserEmails = users.getEmailUserEmails();

        sendRealTimeNotification(notification, notificationUserIds);

        if ("prod".equals(appEnvironment)) { // Only send emails in production
            sendEmailNotification(notification, emailUserEmails);
        }

        // Persist the notification in the database
        AddNotificationDTO notificationDTO = NotificationDTOMapper.mapNotificationToAddNotificationDTO(notification, notificationUserIds);
        notificationDTO.setUserIds(notificationUserIds);
        notificationPersistenceService.addNotification(notificationDTO);
    }

    private void sendRealTimeNotification(Notification notification, List<String> userIds) {
        System.out.println("Sending notification: " + notification);
        try {
            String serializedNotification = objectMapper.writeValueAsString(notification);

            // Send the event to all users connected to the WebSocket
            for (String userId : userIds) {
                if (messagingService.getSessions().containsKey(userId)) {
                    messagingService.sendMessageToUser(userId, serializedNotification);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmailNotification(Notification notification, List<String> userEmails) {
        System.out.println("Sending email notification: " + notification);
        for (String userEmail : userEmails) {
            emailService.sendEmail(userEmail, notification.getTitle(), notification.getMessage());
        }
    }
}
