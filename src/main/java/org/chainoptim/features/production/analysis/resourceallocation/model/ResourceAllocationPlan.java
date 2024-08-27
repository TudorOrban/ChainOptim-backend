package org.chainoptim.features.production.analysis.resourceallocation.model;


import org.chainoptim.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "active_resource_allocation_plans")
public class ResourceAllocationPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "factory_id", nullable = false)
    private Integer factoryId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "activation_date")
    private LocalDateTime activationDate;

    @Column(name = "is_active")
    private Boolean isActive;

    // Manual deserialization and caching of JSON column
    @Column(name = "allocation_plan", columnDefinition = "json")
    private String allocationPlanJson;

    @Transient // Ignore field
    private AllocationPlan allocationPlan;

    public AllocationPlan getAllocationPlan() {
        if (this.allocationPlan == null && this.allocationPlanJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            try {
                this.allocationPlan = mapper.readValue(this.allocationPlanJson, AllocationPlan.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid Allocation Plan json");
            }
        }
        return this.allocationPlan;
    }

    public void setAllocationPlan(AllocationPlan allocationPlan) {
        this.allocationPlan = allocationPlan;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            this.allocationPlanJson = mapper.writeValueAsString(allocationPlan);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ValidationException("Invalid Allocation Plan json");
        }
    }
}
