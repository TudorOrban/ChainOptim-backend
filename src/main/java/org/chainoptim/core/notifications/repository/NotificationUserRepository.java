package org.chainoptim.core.notifications.repository;

import org.chainoptim.core.notifications.model.NotificationUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationUserRepository extends JpaRepository<NotificationUser, Integer>, NotificationSearchRepository {

    List<NotificationUser> findByUserId(String userId);
}
