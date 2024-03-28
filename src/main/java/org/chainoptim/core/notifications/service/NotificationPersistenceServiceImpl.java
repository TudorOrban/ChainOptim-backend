package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.dto.AddNotificationDTO;
import org.chainoptim.core.notifications.dto.NotificationDTOMapper;
import org.chainoptim.core.notifications.dto.UpdateNotificationDTO;
import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.core.notifications.repository.NotificationRepository;
import org.chainoptim.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationPersistenceServiceImpl implements NotificationPersistenceService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationPersistenceServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getNotificationsByUserId(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    public Notification addNotification(AddNotificationDTO notificationDTO) {
        return notificationRepository.save(NotificationDTOMapper.mapAddNotificationDTOToNotification(notificationDTO));
    }

    public Notification updateNotification(UpdateNotificationDTO notificationDTO) {
        Notification notification = notificationRepository.findById(notificationDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Notification with ID: " + notificationDTO.getId() + " not found"));

        Notification updatedNotification = NotificationDTOMapper.setUpdateNotificationDTOToNotification(notificationDTO, notification);

        return notificationRepository.save(updatedNotification);
    }

    public void deleteNotification(Integer notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
