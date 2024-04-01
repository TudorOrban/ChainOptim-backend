package org.chainoptim.core.settings.repository;

import org.chainoptim.core.settings.model.UserSettings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Integer> {

    Optional<UserSettings> findByUserId(String userId);
}
