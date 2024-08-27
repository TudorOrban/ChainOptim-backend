package org.chainoptim.core.notifications.controller;

import org.chainoptim.core.email.service.EmailService;
import org.chainoptim.core.notifications.dto.AddNotificationDTO;
import org.chainoptim.core.notifications.dto.UpdateNotificationDTO;
import org.chainoptim.core.notifications.model.Notification;
import org.chainoptim.core.notifications.model.NotificationUser;
import org.chainoptim.core.notifications.service.NotificationPersistenceService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationPersistenceController { // TODO: Secure all the endpoints

    private final NotificationPersistenceService notificationPersistenceService;
    private final EmailService emailService;

    @Autowired
    public NotificationPersistenceController(NotificationPersistenceService notificationPersistenceService,
                                             EmailService emailService) {
        this.notificationPersistenceService = notificationPersistenceService;
        this.emailService = emailService;
    }

    @GetMapping("/user/{userId}")
    public List<NotificationUser> getNotificationsByUserId(@PathVariable("userId") String userId) {
        return notificationPersistenceService.getNotificationsByUserId(userId);
    }

//    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Supplier\", \"Read\")")
    @GetMapping("/user/advanced/{userId}")
    public ResponseEntity<PaginatedResults<NotificationUser>> getNotificationsByUserIdAdvanced(
            @PathVariable String userId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<NotificationUser> notifications = notificationPersistenceService.getNotificationsByUserIdAdvanced(userId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(notifications);
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
