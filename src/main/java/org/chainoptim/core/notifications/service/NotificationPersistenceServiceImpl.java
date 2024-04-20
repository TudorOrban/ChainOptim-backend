package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.dto.AddNotificationDTO;
import org.chainoptim.core.notifications.dto.NotificationDTOMapper;
import org.chainoptim.core.notifications.dto.UpdateNotificationDTO;
import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.core.notifications.model.NotificationUser;
import org.chainoptim.core.notifications.repository.NotificationRepository;
import org.chainoptim.core.notifications.repository.NotificationUserRepository;
import org.chainoptim.core.user.model.User;
import org.chainoptim.core.user.repository.UserRepository;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotificationPersistenceServiceImpl implements NotificationPersistenceService {

    private final NotificationRepository notificationRepository;
    private final NotificationUserRepository notificationUserRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NotificationPersistenceServiceImpl(NotificationRepository notificationRepository,
                                              NotificationUserRepository notificationUserRepository,
                                              JdbcTemplate jdbcTemplate) {
        this.notificationRepository = notificationRepository;
        this.notificationUserRepository = notificationUserRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<NotificationUser> getNotificationsByUserId(String userId) {
        return notificationUserRepository.findByUserId(userId);
    }

    public PaginatedResults<NotificationUser> getNotificationsByUserIdAdvanced(String userId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        return notificationUserRepository.findByUserIdAdvanced(userId, searchQuery, sortBy, ascending, page, itemsPerPage);
    }

    /*
     * Use JdbcTemplate for this particular operation
     * as usual .save causes StackOverflow for some reason
     */
    @Transactional
    public Notification addNotification(AddNotificationDTO notificationDTO) {
        Notification notification = notificationRepository.save(NotificationDTOMapper.mapAddNotificationDTOToNotification(notificationDTO));

        List<Object[]> batchArgs = new ArrayList<>();
        for (String userId : notificationDTO.getUserIds()) {
            Object[] values = {
                    notification.getId(),
                    false,
                    userId
            };
            batchArgs.add(values);
        }

        jdbcTemplate.batchUpdate(
                "INSERT INTO notification_users (notification_id, read_status, user_id) VALUES (?, ?, ?)",
                batchArgs
        );

        return notification;
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
