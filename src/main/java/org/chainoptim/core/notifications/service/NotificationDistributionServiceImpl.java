package org.chainoptim.core.notifications.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationDistributionServiceImpl implements NotificationDistributionService {

    public <T> List<String> distributeEventToUsers(T event, String entityType) {
        System.out.println("Distributing event: " + event);
        return List.of("086e9e96-a8ef-11ee-bffa-00155de90539");
    }
}
