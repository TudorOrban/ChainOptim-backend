package org.chainoptim.core.tenant.settings.dto;

import org.chainoptim.core.tenant.settings.model.UserSettings;

public class UserSettingsDTOMapper {

    public static UserSettings mapCreateUserSettingsDTOToUserSettings(CreateUserSettingsDTO createUserSettingsDTO) {
        UserSettings userSettings = new UserSettings();
        userSettings.setUserId(createUserSettingsDTO.getUserId());
        userSettings.setGeneralSettings(createUserSettingsDTO.getGeneralSettings());
        userSettings.setNotificationSettings(createUserSettingsDTO.getNotificationSettings());
        return userSettings;
    }

    public static void setUpdateUserSettingsDTOToUserSettings(UserSettings userSettings, UpdateUserSettingsDTO updateUserSettingsDTO) {
        userSettings.setGeneralSettings(updateUserSettingsDTO.getGeneralSettings());
        userSettings.setNotificationSettings(updateUserSettingsDTO.getNotificationSettings());
    }
}
