package org.chainoptim.features.scanalysis.production.productgraph.model;

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
@Table(name = "product_production_graphs")
public class ProductProductionGraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Manual deserialization and caching of JSON column
    @Column(name = "product_graph", columnDefinition = "json")
    private String productGraphJson;

    @Transient // Ignore field
    private ProductGraph productGraph;

    public ProductGraph getProductGraph() {
        if (this.productGraph == null && this.productGraphJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.productGraph = mapper.readValue(this.productGraphJson, ProductGraph.class);
            } catch (JsonProcessingException e) {
                System.out.println("Failed to deserialize productGraphJson: " + productGraphJson + "Error: " + e);
                throw new ValidationException("Invalid Product Graph data");
            }
        }
        return this.productGraph;
    }

    public void setProductGraph(ProductGraph productGraph) {
        this.productGraph = productGraph;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.productGraphJson = mapper.writeValueAsString(productGraph);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Product Graph data");
        }
    }

}
