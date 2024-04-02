package org.chainoptim.core.settings.service;

import org.chainoptim.core.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.core.settings.model.UserSettings;

public interface UserSettingsService {

    UserSettings getUserSettings(String userId);

    UserSettings saveUserSettings(CreateUserSettingsDTO userSettingsDTO);

    UserSettings updateUserSettings(UpdateUserSettingsDTO userSettingsDTO);

    void deleteUserSettings(Integer id);
}
