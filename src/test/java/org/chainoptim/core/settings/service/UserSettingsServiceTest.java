package org.chainoptim.core.settings.service;

import org.chainoptim.core.tenant.settings.model.GeneralSettings;
import org.chainoptim.core.tenant.settings.model.NotificationSettings;
import org.chainoptim.core.tenant.settings.service.UserSettingsServiceImpl;
import org.chainoptim.core.tenant.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.core.tenant.settings.dto.UserSettingsDTOMapper;
import org.chainoptim.core.tenant.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.tenant.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.core.tenant.settings.model.UserSettings;
import org.chainoptim.core.tenant.settings.repository.UserSettingsRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSettingsServiceTest {

    @Mock
    private UserSettingsRepository userSettingsRepository;
    @Mock
    private SubscriptionPlanLimiterService planLimiterService;
    @Mock
    private EntitySanitizerService entitySanitizerService;

    @InjectMocks
    private UserSettingsServiceImpl userSettingsService;

    @Test
    void testCreateUserSettings() {
        // Arrange
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setInfoLevel(GeneralSettings.InfoLevel.ADVANCED);
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setSupplierOrdersOn(true);
        CreateUserSettingsDTO userSettingsDTO = new CreateUserSettingsDTO("user-id", generalSettings, notificationSettings);
        UserSettings expectedUserSettings = UserSettingsDTOMapper.mapCreateUserSettingsDTOToUserSettings(userSettingsDTO);

        when(userSettingsRepository.save(any(UserSettings.class))).thenReturn(expectedUserSettings);
        when(entitySanitizerService.sanitizeCreateUserSettingsDTO(any(CreateUserSettingsDTO.class))).thenReturn(userSettingsDTO);

        // Act
        UserSettings createdUserSettings = userSettingsService.saveUserSettings(userSettingsDTO);

        // Assert
        assertNotNull(createdUserSettings);
        assertEquals(expectedUserSettings.getUserId(), createdUserSettings.getUserId());
        assertEquals(expectedUserSettings.getGeneralSettings().getInfoLevel(), createdUserSettings.getGeneralSettings().getInfoLevel());
        assertEquals(expectedUserSettings.getNotificationSettings().isSupplierOrdersOn(), createdUserSettings.getNotificationSettings().isSupplierOrdersOn());
    }

    @Test
    void testUpdateUserSettings_ExistingUserSettings() {
        // Arrange
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setInfoLevel(GeneralSettings.InfoLevel.ADVANCED);
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setSupplierOrdersOn(true);
        UpdateUserSettingsDTO userSettingsDTO = new UpdateUserSettingsDTO(1, "user-id", generalSettings, notificationSettings);
        UserSettings existingUserSettings = new UserSettings();
        existingUserSettings.setId(1);

        when(userSettingsRepository.findById(1)).thenReturn(Optional.of(existingUserSettings));
        when(userSettingsRepository.save(any(UserSettings.class))).thenReturn(existingUserSettings);
        when(entitySanitizerService.sanitizeUpdateUserSettingsDTO(any(UpdateUserSettingsDTO.class))).thenReturn(userSettingsDTO);

        // Act
        UserSettings updatedUserSettings = userSettingsService.updateUserSettings(userSettingsDTO);

        // Assert
        assertNotNull(updatedUserSettings);
        assertEquals(existingUserSettings.getUserId(), updatedUserSettings.getUserId());
        assertEquals(existingUserSettings.getGeneralSettings().getInfoLevel(), updatedUserSettings.getGeneralSettings().getInfoLevel());
        assertEquals(existingUserSettings.getNotificationSettings().isSupplierOrdersOn(), updatedUserSettings.getNotificationSettings().isSupplierOrdersOn());

        verify(userSettingsRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateUserSettings_NonExistingUserSettings() {
        // Arrange
        GeneralSettings generalSettings = new GeneralSettings();
        generalSettings.setInfoLevel(GeneralSettings.InfoLevel.ADVANCED);
        NotificationSettings notificationSettings = new NotificationSettings();
        notificationSettings.setSupplierOrdersOn(true);
        UpdateUserSettingsDTO userSettingsDTO = new UpdateUserSettingsDTO(1, "user-id", generalSettings, notificationSettings);
        UserSettings existingUserSettings = new UserSettings();
        existingUserSettings.setId(1);

        when(userSettingsRepository.findById(1)).thenReturn(Optional.empty());
        when(entitySanitizerService.sanitizeUpdateUserSettingsDTO(any(UpdateUserSettingsDTO.class))).thenReturn(userSettingsDTO);

        // Act and assert
        assertThrows(ResourceNotFoundException.class, () -> userSettingsService.updateUserSettings(userSettingsDTO));

        verify(userSettingsRepository, times(1)).findById(1);
        verify(userSettingsRepository, never()).save(any(UserSettings.class));
    }

    @Test
    void testDeleteUserSettings() {
        // Arrange
        doNothing().when(userSettingsRepository).deleteById(any(Integer.class));

        // Act
        userSettingsService.deleteUserSettings(1);

        // Assert
        verify(userSettingsRepository, times(1)).deleteById(any(Integer.class));
    }
}
