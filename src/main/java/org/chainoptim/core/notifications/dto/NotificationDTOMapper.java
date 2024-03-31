package org.chainoptim.core.notifications.dto;

import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.core.user.model.User;

public class NotificationDTOMapper {

    public static Notification mapAddNotificationDTOToNotification(AddNotificationDTO addNotificationDTO) {
        Notification notification = new Notification();
        notification.setTitle(addNotificationDTO.getTitle());
        notification.setEntityId(addNotificationDTO.getEntityId());
        notification.setEntityType(addNotificationDTO.getEntityType());
        notification.setMessage(addNotificationDTO.getMessage());
        notification.setReadStatus(addNotificationDTO.getReadStatus());
        notification.setType(addNotificationDTO.getType());

        return notification;
    }

    public static AddNotificationDTO mapNotificationToAddNotificationDTO(Notification notification) {
        AddNotificationDTO addNotificationDTO = new AddNotificationDTO();
        addNotificationDTO.setTitle(notification.getTitle());
        addNotificationDTO.setEntityId(notification.getEntityId());
        addNotificationDTO.setEntityType(notification.getEntityType());
        addNotificationDTO.setMessage(notification.getMessage());
        addNotificationDTO.setReadStatus(notification.getReadStatus());
        addNotificationDTO.setType(notification.getType());

        return addNotificationDTO;
    }

    public static Notification setUpdateNotificationDTOToNotification(UpdateNotificationDTO updateNotificationDTO, Notification notification) {
        notification.setTitle(updateNotificationDTO.getTitle());
        notification.setEntityId(updateNotificationDTO.getEntityId());
        notification.setEntityType(updateNotificationDTO.getEntityType());
        notification.setMessage(updateNotificationDTO.getMessage());
        notification.setReadStatus(updateNotificationDTO.getReadStatus());
        notification.setType(updateNotificationDTO.getType());

        return notification;
    }
}
