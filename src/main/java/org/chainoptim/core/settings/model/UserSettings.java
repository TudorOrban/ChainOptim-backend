package org.chainoptim.core.settings.model;

import org.chainoptim.exception.ValidationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    // Manual deserialization and caching of JSON column
    @Column(name = "notification_settings", columnDefinition = "json")
    private String notificationSettingsJson;

    @Transient // Ignore field
    private NotificationSettings notificationSettings;

    public NotificationSettings getNotificationSettings() {
        if (this.notificationSettings == null && this.notificationSettingsJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.notificationSettings = mapper.readValue(this.notificationSettingsJson, NotificationSettings.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid Notification Settings json");
            }
        }
        return this.notificationSettings;
    }

    public void setNotificationSettings(NotificationSettings notificationSettings) {
        this.notificationSettings = notificationSettings;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.notificationSettingsJson = mapper.writeValueAsString(notificationSettings);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Notification Settings json");
        }
    }
}
