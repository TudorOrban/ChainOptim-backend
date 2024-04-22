package org.chainoptim.core.settings.service;

import org.chainoptim.core.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.core.settings.dto.UserSettingsDTOMapper;
import org.chainoptim.core.settings.model.UserSettings;
import org.chainoptim.core.settings.repository.UserSettingsRepository;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsServiceImpl implements UserSettingsService {

    private final UserSettingsRepository userSettingsRepository;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public UserSettingsServiceImpl(UserSettingsRepository userSettingsRepository,
                                   EntitySanitizerService entitySanitizerService) {
        this.userSettingsRepository = userSettingsRepository;
        this.entitySanitizerService = entitySanitizerService;
    }

    public UserSettings getUserSettings(String userId) {
        return userSettingsRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User settings for user with ID: " + userId + " not found"));
    }

    public UserSettings saveUserSettings(CreateUserSettingsDTO userSettingsDTO) {
        CreateUserSettingsDTO sanitizedUserSettingsDTO = entitySanitizerService.sanitizeCreateUserSettingsDTO(userSettingsDTO);
        UserSettings userSettings = UserSettingsDTOMapper.mapCreateUserSettingsDTOToUserSettings(sanitizedUserSettingsDTO);
        return userSettingsRepository.save(userSettings);
    }

    public UserSettings updateUserSettings(UpdateUserSettingsDTO userSettingsDTO) {
        UpdateUserSettingsDTO sanitizedUserSettingsDTO = entitySanitizerService.sanitizeUpdateUserSettingsDTO(userSettingsDTO);
        UserSettings userSettings = userSettingsRepository.findById(sanitizedUserSettingsDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User settings with ID: " + sanitizedUserSettingsDTO.getId() + " not found"));
        UserSettingsDTOMapper.setUpdateUserSettingsDTOToUserSettings(userSettings, sanitizedUserSettingsDTO);
        return userSettingsRepository.save(userSettings);
    }

    @Transactional
    public void deleteUserSettings(Integer id) {
        userSettingsRepository.deleteById(id);
    }
}
