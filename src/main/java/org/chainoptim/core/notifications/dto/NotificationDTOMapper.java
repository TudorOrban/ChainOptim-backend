package org.chainoptim.core.notifications.dto;

import org.chainoptim.core.notifications.model.Notification;

public class NotificationDTOMapper {

    public static Notification mapAddNotificationDTOToNotification(AddNotificationDTO addNotificationDTO) {
        Notification notification = new Notification();
        notification.setUserId(addNotificationDTO.getUserId());
        notification.setTitle(addNotificationDTO.getTitle());
        notification.setEntityId(addNotificationDTO.getEntityId());
        notification.setEntityType(addNotificationDTO.getEntityType());
        notification.setMessage(addNotificationDTO.getMessage());
        notification.setReadStatus(addNotificationDTO.getReadStatus());
        notification.setType(addNotificationDTO.getType());

        return notification;
    }

    public static Notification setUpdateNotificationDTOToNotification(UpdateNotificationDTO updateNotificationDTO, Notification notification) {
        notification.setUserId(updateNotificationDTO.getUserId());
        notification.setTitle(updateNotificationDTO.getTitle());
        notification.setEntityId(updateNotificationDTO.getEntityId());
        notification.setEntityType(updateNotificationDTO.getEntityType());
        notification.setMessage(updateNotificationDTO.getMessage());
        notification.setReadStatus(updateNotificationDTO.getReadStatus());
        notification.setType(updateNotificationDTO.getType());

        return notification;
    }
}
