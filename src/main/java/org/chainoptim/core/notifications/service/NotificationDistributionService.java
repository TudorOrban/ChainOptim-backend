package org.chainoptim.core.notifications.service;

import java.util.List;

public interface NotificationDistributionService {

    <T> List<String> distributeEventToUsers(T event, String entityType);
}
