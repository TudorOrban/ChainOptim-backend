package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.dto.AddNotificationDTO;
import org.chainoptim.core.notifications.dto.UpdateNotificationDTO;
import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.core.notifications.model.NotificationUser;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface NotificationPersistenceService {

    List<NotificationUser> getNotificationsByUserId(String userId);
    PaginatedResults<NotificationUser> getNotificationsByUserIdAdvanced(String userId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);

    Notification addNotification(AddNotificationDTO notification);
    Notification updateNotification(UpdateNotificationDTO notification);
    void deleteNotification(Integer notificationId);
}
