package org.chainoptim.core.settings.service;

import org.chainoptim.core.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.core.settings.dto.UserSettingsDTOMapper;
import org.chainoptim.core.settings.model.UserSettings;
import org.chainoptim.core.settings.repository.UserSettingsRepository;
import org.chainoptim.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsServiceImpl implements UserSettingsService {

    private final UserSettingsRepository userSettingsRepository;

    @Autowired
    public UserSettingsServiceImpl(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    public UserSettings getUserSettings(String userId) {
        return userSettingsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User settings for user with ID: " + userId + " not found"));
    }

    public UserSettings saveUserSettings(CreateUserSettingsDTO userSettingsDTO) {
        // TODO: Sanitize input
        UserSettings userSettings = UserSettingsDTOMapper.mapCreateUserSettingsDTOToUserSettings(userSettingsDTO);
        return userSettingsRepository.save(userSettings);
    }

    public UserSettings updateUserSettings(UpdateUserSettingsDTO userSettingsDTO) {
        // TODO: Sanitize input
        UserSettings userSettings = userSettingsRepository.findById(userSettingsDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User settings with ID: " + userSettingsDTO.getId() + " not found"));
        UserSettingsDTOMapper.setUpdateUserSettingsDTOToUserSettings(userSettings, userSettingsDTO);
        return userSettingsRepository.save(userSettings);
    }

    @Transactional
    public void deleteUserSettings(Integer id) {
        userSettingsRepository.deleteById(id);
    }
}
