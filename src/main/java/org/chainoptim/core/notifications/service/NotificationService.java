package org.chainoptim.core.notifications.service;

public interface NotificationService {

    <T> void processEvent(T event, String entityType);
}
