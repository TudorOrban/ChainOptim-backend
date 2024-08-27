package org.chainoptim.core.tenant.settings.service;

import org.chainoptim.core.tenant.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.tenant.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.core.tenant.settings.model.UserSettings;

public interface UserSettingsService {

    UserSettings getUserSettings(String userId);

    UserSettings saveUserSettings(CreateUserSettingsDTO userSettingsDTO);

    UserSettings updateUserSettings(UpdateUserSettingsDTO userSettingsDTO);

    void deleteUserSettings(Integer id);
}
