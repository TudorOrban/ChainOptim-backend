package org.chainoptim.core.tenant.settings.dto;

import org.chainoptim.core.tenant.settings.model.GeneralSettings;
import org.chainoptim.core.tenant.settings.model.NotificationSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserSettingsDTO {

    private Integer id;
    private String userId;
    private GeneralSettings generalSettings;
    private NotificationSettings notificationSettings;
}
