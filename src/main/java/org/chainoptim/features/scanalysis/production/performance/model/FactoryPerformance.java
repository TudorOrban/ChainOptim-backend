package org.chainoptim.features.scanalysis.production.performance.model;

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
@Table(name = "factory_performances")
public class FactoryPerformance {

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

    // Manual deserialization and caching of JSON column
    @Column(name = "factory_performance_report", columnDefinition = "json")
    private String reportJson;

    @Transient // Ignore field
    private FactoryPerformanceReport report;

    public FactoryPerformanceReport getReport() {
        if (this.report == null && this.reportJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            try {
                this.report = mapper.readValue(this.reportJson, FactoryPerformanceReport.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid Report json");
            }
        }
        return this.report;
    }

    public void setReport(FactoryPerformanceReport report) {
        this.report = report;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            this.reportJson = mapper.writeValueAsString(report);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ValidationException("Invalid Report json");
        }
    }
}
