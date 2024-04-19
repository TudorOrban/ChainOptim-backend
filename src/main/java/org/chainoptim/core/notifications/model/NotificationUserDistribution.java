package org.chainoptim.core.notifications.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationUserDistribution {

    private List<String> notificationUserIds;
    private List<String> emailUserEmails;
}
