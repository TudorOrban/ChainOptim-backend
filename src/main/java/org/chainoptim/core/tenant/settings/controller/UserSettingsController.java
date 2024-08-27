package org.chainoptim.core.tenant.settings.controller;

import org.chainoptim.core.general.security.service.SecurityService;
import org.chainoptim.core.tenant.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.tenant.settings.dto.DeleteUserSettingsDTO;
import org.chainoptim.core.tenant.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.core.tenant.settings.model.UserSettings;
import org.chainoptim.core.tenant.settings.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-settings")
public class UserSettingsController { // TODO: Secure these endpoints

    private final UserSettingsService userSettingsService;
    private final SecurityService securityService;

    @Autowired
    public UserSettingsController(UserSettingsService userSettingsService,
                                  SecurityService securityService) {
        this.userSettingsService = userSettingsService;
        this.securityService = securityService;
    }

    @PreAuthorize("@securityService.canUserAccessOrganizationEntity(#userId, \"Read\")")
    @GetMapping("/user/{userId}")
    public UserSettings getUserSettings(@PathVariable("userId") String userId) {
        return userSettingsService.getUserSettings(userId);
    }

    @PreAuthorize("@securityService.canUserAccessOrganizationEntity(#userSettingsDTO.getUserId(), \"Create\")")
    @PostMapping("create")
    public UserSettings createUserSettings(@RequestBody CreateUserSettingsDTO userSettingsDTO) {
        return userSettingsService.saveUserSettings(userSettingsDTO);
    }

    @PreAuthorize("@securityService.canUserAccessOrganizationEntity(#userSettingsDTO.getUserId(), \"Update\")")
    @PutMapping("update")
    public UserSettings updateUserSettings(@RequestBody UpdateUserSettingsDTO userSettingsDTO) {
        return userSettingsService.updateUserSettings(userSettingsDTO);
    }

    @PreAuthorize("@securityService.canUserAccessOrganizationEntity(#userSettingsDTO.getUserId(), \"Delete\")")
    @DeleteMapping("/delete")
    public void deleteUserSettings(@RequestBody DeleteUserSettingsDTO userSettingsDTO) {
        userSettingsService.deleteUserSettings(userSettingsDTO.getId());
    }
}
