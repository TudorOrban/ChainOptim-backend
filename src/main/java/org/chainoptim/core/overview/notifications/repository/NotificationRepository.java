package org.chainoptim.core.overview.notifications.repository;

import org.chainoptim.core.overview.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
