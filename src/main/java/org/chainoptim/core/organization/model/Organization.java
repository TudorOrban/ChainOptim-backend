package org.chainoptim.core.organization.model;

import org.chainoptim.core.subscription.model.PlanDetails;
import org.chainoptim.core.subscription.model.BaseSubscriptionPlans;
import org.chainoptim.core.user.model.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<User> users;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_tier", nullable = false)
    private SubscriptionPlanTier subscriptionPlanTier;

    @Column(name = "is_plan_basic")
    private Boolean isPlanBasic;

    @Column(name = "is_plan_active")
    private Boolean isPlanActive;

    public PlanDetails getSubscriptionPlan() {
        return BaseSubscriptionPlans.getPlan(subscriptionPlanTier);
    }

    @PrePersist
    protected void onCreate() {
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
        Organization that = (Organization) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
