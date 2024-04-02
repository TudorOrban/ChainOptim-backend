package org.chainoptim.core.settings.repository;

import org.chainoptim.core.settings.model.UserSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Integer> {

    Optional<UserSettings> findByUserId(String userId);

    @Query("SELECT us FROM UserSettings us WHERE us.userId IN :userIds")
    List<UserSettings> findByUserIdIn(List<String> userIds);
}
