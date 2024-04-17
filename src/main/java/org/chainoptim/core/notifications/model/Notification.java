package org.chainoptim.core.notifications.model;

import org.chainoptim.exception.ValidationException;
import org.chainoptim.shared.enums.Feature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "notification")
    @JsonIgnore
    private Set<NotificationUser> notificationUsers = new HashSet<>();

    @Column(name = "title")
    private String title;

    @Column(name = "entity_id")
    private Integer entityId;

    @Column(name = "entity_type")
    private Feature entityType;

    @Column(name = "message")
    private String message;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "read_status")
    private Boolean readStatus;

    @Column(name = "type")
    private String type;

    // Manual deserialization and caching of JSON columns
    @Column(name = "extra_info", columnDefinition = "json")
    private String extraInfoJson;

    @Transient // Ignore field
    private NotificationExtraInfo extraInfo;

    public NotificationExtraInfo getExtraInfo() {
        if (this.extraInfo == null && this.extraInfoJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.extraInfo = mapper.readValue(this.extraInfoJson, NotificationExtraInfo.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid NotificationExtraInfo json");
            }
        }
        return this.extraInfo;
    }

    public void setExtraInfo(NotificationExtraInfo extraInfo) {
        this.extraInfo = extraInfo;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.extraInfoJson = mapper.writeValueAsString(extraInfo);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid NotificationExtraInfo json");
        }
    }
}
