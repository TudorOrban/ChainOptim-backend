package org.chainoptim.core.settings.dto;

import org.chainoptim.core.settings.model.GeneralSettings;
import org.chainoptim.core.settings.model.NotificationSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserSettingsDTO {

    private String userId;
    private GeneralSettings generalSettings;
    private NotificationSettings notificationSettings;
}
