package org.chainoptim.core.notifications.repository;

import org.chainoptim.core.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
