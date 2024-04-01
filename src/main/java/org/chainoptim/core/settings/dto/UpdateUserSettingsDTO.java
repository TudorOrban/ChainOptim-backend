package org.chainoptim.core.settings.dto;

import org.chainoptim.core.settings.model.NotificationSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserSettingsDTO {

    private Integer id;
    private String userId;
    private NotificationSettings notificationSettings;
}
