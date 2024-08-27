package org.chainoptim.core.tenant.settings.model;

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

    // Manual deserialization and caching of JSON columns
    @Column(name = "general_settings", columnDefinition = "json")
    private String generalSettingsJson;

    @Transient // Ignore field
    private GeneralSettings generalSettings;

    public GeneralSettings getGeneralSettings() {
        if (this.generalSettings == null && this.generalSettingsJson != null) {
            // Remove backslashes used to escape double quotes
            String correctedJson = this.generalSettingsJson.replace("\\\"", "\"").trim();

            // Ensure there are no outer quotes surrounding the JSON object
            if (correctedJson.startsWith("\"") && correctedJson.endsWith("\"")) {
                correctedJson = correctedJson.substring(1, correctedJson.length() - 1);
            }

            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.generalSettings = mapper.readValue(correctedJson, GeneralSettings.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid General Settings json");
            }
        }
        return this.generalSettings;
    }

    public void setGeneralSettings(GeneralSettings generalSettings) {
        this.generalSettings = generalSettings;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.generalSettingsJson = mapper.writeValueAsString(generalSettings);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid General Settings json");
        }
    }

    @Column(name = "notification_settings", columnDefinition = "json")
    private String notificationSettingsJson;

    @Transient // Ignore field
    private NotificationSettings notificationSettings;

    public NotificationSettings getNotificationSettings() {
        if (this.notificationSettings == null && this.notificationSettingsJson != null) {
            // Remove backslashes used to escape double quotes
            String correctedJson = this.notificationSettingsJson.replace("\\\"", "\"").trim();

            // Ensure there are no outer quotes surrounding the JSON object
            if (correctedJson.startsWith("\"") && correctedJson.endsWith("\"")) {
                correctedJson = correctedJson.substring(1, correctedJson.length() - 1);
            }

            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.notificationSettings = mapper.readValue(correctedJson, NotificationSettings.class);
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
