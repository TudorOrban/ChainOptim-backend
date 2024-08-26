package org.chainoptim.core.subscription.model;

import org.chainoptim.core.settings.model.GeneralSettings;
import org.chainoptim.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_basic")
    private Boolean isBasic;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "last_payment_date")
    private LocalDateTime lastPaymentDate;

    // Manual deserialization and caching of JSON columns
    @Column(name = "custom_plan", columnDefinition = "json")
    private String customPlanJson;

    @Transient // Ignore field
    private CustomSubscriptionPlan customPlan;

    public CustomSubscriptionPlan getCustomPlan() {
        if (this.customPlan == null && this.customPlanJson != null) {
            // Remove backslashes used to escape double quotes
            String correctedJson = this.customPlanJson.replace("\\\"", "\"").trim();

            // Ensure there are no outer quotes surrounding the JSON object
            if (correctedJson.startsWith("\"") && correctedJson.endsWith("\"")) {
                correctedJson = correctedJson.substring(1, correctedJson.length() - 1);
            }

            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.customPlan = mapper.readValue(correctedJson, CustomSubscriptionPlan.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid Custom Plan json");
            }
        }
        return this.customPlan;
    }

    public void setCustomPlan(CustomSubscriptionPlan customPlan) {
        this.customPlan = customPlan;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.customPlanJson = mapper.writeValueAsString(customPlan);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Custom Plan json");
        }
    }

}
