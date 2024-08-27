package org.chainoptim.features.production.analysis.productionhistory.model;

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
@Table(name = "factory_production_histories")
public class FactoryProductionHistory {

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

    @Column(name = "s3_key")
    private String s3Key;

    @Column(name = "production_history", columnDefinition = "json")
    private String productionHistoryJson;

    @Transient
    private ProductionHistory productionHistory;

    public ProductionHistory getProductionHistory() {
        if (this.productionHistory == null && this.productionHistoryJson != null) {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            try {
                this.productionHistory = mapper.readValue(this.productionHistoryJson, ProductionHistory.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid Production History data");
            }
        }
        return this.productionHistory;
    }

    public void setProductionHistory(ProductionHistory productionHistory) {
        this.productionHistory = productionHistory;
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            this.productionHistoryJson = mapper.writeValueAsString(productionHistory);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Production History data");
        }
    }
}
