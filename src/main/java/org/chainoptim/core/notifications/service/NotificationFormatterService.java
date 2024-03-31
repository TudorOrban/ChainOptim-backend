package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.Notification;

public interface NotificationFormatterService {

    <T> Notification formatEvent(T event, String entityType);
}
