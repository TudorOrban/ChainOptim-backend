package org.chainoptim.features.scanalysis.production.factorygraph.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.exception.ValidationException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factory_production_graphs")
public class FactoryProductionGraph {

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
    @Column(name = "factory_graph", columnDefinition = "json")
    private String factoryGraphJson;

    @Transient // Ignore field
    private FactoryGraph factoryGraph;

    public FactoryGraph getFactoryGraph() {
        if (this.factoryGraph == null && this.factoryGraphJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.factoryGraph = mapper.readValue(this.factoryGraphJson, FactoryGraph.class);
            } catch (JsonProcessingException e) {
                System.out.println("Failed to deserialize factoryGraphJson: " + factoryGraphJson + "Error: " + e);
                throw new ValidationException("Invalid Factory Graph data");
            }
        }
        return this.factoryGraph;
    }

    public void setFactoryGraph(FactoryGraph factoryGraph) {
        this.factoryGraph = factoryGraph;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.factoryGraphJson = mapper.writeValueAsString(factoryGraph);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Factory Graph data");
        }
    }

}
