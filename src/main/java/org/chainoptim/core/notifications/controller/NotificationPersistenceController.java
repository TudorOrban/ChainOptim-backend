package org.chainoptim.core.notifications.controller;

import org.chainoptim.core.email.service.EmailService;
import org.chainoptim.core.notifications.dto.AddNotificationDTO;
import org.chainoptim.core.notifications.dto.UpdateNotificationDTO;
import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.core.notifications.model.NotificationUser;
import org.chainoptim.core.notifications.service.NotificationPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationPersistenceController {

    private final NotificationPersistenceService notificationPersistenceService;
    private final EmailService emailService;

    @Autowired
    public NotificationPersistenceController(NotificationPersistenceService notificationPersistenceService,
                                             EmailService emailService) {
        this.notificationPersistenceService = notificationPersistenceService;
        this.emailService = emailService;
    }

    @GetMapping("/send-email")
    public ResponseEntity<Void> sendEmail() {
        emailService.sendEmail(
                "tudororban3@gmail.com",
                "Welcome to ChainOptim!",
                "Thank you for signing up with us. Here are some things you can do next..."
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public List<NotificationUser> getNotificationsByUserId(@PathVariable("userId") String userId) {
        return notificationPersistenceService.getNotificationsByUserId(userId);
    }

    @PostMapping("/add")
    public Notification saveNotification(@RequestBody AddNotificationDTO notificationDTO) {
        return notificationPersistenceService.addNotification(notificationDTO);
    }

    @PutMapping("/update")
    public Notification updateNotification(@RequestBody UpdateNotificationDTO notificationDTO) {
        return notificationPersistenceService.updateNotification(notificationDTO);
    }

    @DeleteMapping("/delete/{notificationId}")
    public void deleteNotification(@PathVariable("notificationId") Integer notificationId) {
        notificationPersistenceService.deleteNotification(notificationId);
    }
}
