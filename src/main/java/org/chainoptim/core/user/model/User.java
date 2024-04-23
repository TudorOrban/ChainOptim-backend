package org.chainoptim.core.user.model;

import org.chainoptim.core.notifications.model.NotificationUser;
import org.chainoptim.core.organization.model.CustomRole;
import org.chainoptim.core.organization.model.Organization;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", nullable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private java.time.LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    @JsonBackReference
    private Organization organization;

    // Role
    public enum Role implements Serializable {
        ADMIN,
        MEMBER,
        NONE
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_role_id", referencedColumnName = "id")
    private CustomRole customRole;

    @Column(name = "is_profile_public")
    private Boolean isProfilePublic;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "verification_token_expiration_date")
    private LocalDateTime verificationTokenExpirationDate;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "is_first_confirmation_email")
    private Boolean isFirstConfirmationEmail;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
