package org.chainoptim.core.tenant.customrole.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.chainoptim.core.tenant.user.model.User;
import org.chainoptim.exception.ValidationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "custom_roles")
public class CustomRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customRole", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;

    // Manual deserialization and caching of JSON column
    @Column(name = "permissions", columnDefinition = "json")
    private String permissionsJson;

    @Transient // Ignore field
    private Permissions permissions;

    public Permissions getPermissions() {
        if (this.permissions == null && this.permissionsJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.permissions = mapper.readValue(this.permissionsJson, Permissions.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid Permissions json");
            }
        }
        return this.permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.permissionsJson = mapper.writeValueAsString(permissions);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Permissions json");
        }
    }
}
