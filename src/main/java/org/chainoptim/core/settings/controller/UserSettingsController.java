package org.chainoptim.core.settings.controller;

import org.chainoptim.core.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.core.settings.model.UserSettings;
import org.chainoptim.core.settings.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-settings")
public class UserSettingsController { // TODO: Secure these endpoints

    private final UserSettingsService userSettingsService;

    @Autowired
    public UserSettingsController(UserSettingsService userSettingsService) {
        this.userSettingsService = userSettingsService;
    }

    @GetMapping("/user/{userId}")
    public UserSettings getUserSettings(@PathVariable("userId") String userId) {
        return userSettingsService.getUserSettings(userId);
    }

    @PostMapping("create")
    public UserSettings createUserSettings(@RequestBody CreateUserSettingsDTO userSettingsDTO) {
        return userSettingsService.saveUserSettings(userSettingsDTO);
    }

    @PutMapping("update")
    public UserSettings updateUserSettings(@RequestBody UpdateUserSettingsDTO userSettingsDTO) {
        return userSettingsService.updateUserSettings(userSettingsDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserSettings(@PathVariable("id") Integer id) {
        userSettingsService.deleteUserSettings(id);
    }
}
