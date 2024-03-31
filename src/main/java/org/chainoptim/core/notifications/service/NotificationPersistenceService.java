package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.dto.AddNotificationDTO;
import org.chainoptim.core.notifications.dto.UpdateNotificationDTO;
import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.core.notifications.model.NotificationUser;

import java.util.List;

public interface NotificationPersistenceService {

    List<NotificationUser> getNotificationsByUserId(String userId);

    Notification addNotification(AddNotificationDTO notification);
    Notification updateNotification(UpdateNotificationDTO notification);
    void deleteNotification(Integer notificationId);
}
