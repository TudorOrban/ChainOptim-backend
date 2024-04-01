package org.chainoptim.core.settings.dto;

import org.chainoptim.core.settings.model.UserSettings;

public class UserSettingsDTOMapper {

    public static UserSettings mapCreateUserSettingsDTOToUserSettings(CreateUserSettingsDTO createUserSettingsDTO) {
        UserSettings userSettings = new UserSettings();
        userSettings.setUserId(createUserSettingsDTO.getUserId());
        userSettings.setNotificationSettings(createUserSettingsDTO.getNotificationSettings());
        return userSettings;
    }

    public static UserSettings setUpdateUserSettingsDTOToUserSettings(UserSettings userSettings, UpdateUserSettingsDTO updateUserSettingsDTO) {
        userSettings.setNotificationSettings(updateUserSettingsDTO.getNotificationSettings());
        return userSettings;
    }
}
