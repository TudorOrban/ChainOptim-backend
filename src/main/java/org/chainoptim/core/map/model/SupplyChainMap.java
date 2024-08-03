package org.chainoptim.core.map.model;

import org.chainoptim.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Table(name = "supply_chain_maps")
public class SupplyChainMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Manual deserialization and caching of JSON column
    @Column(name = "map_data", columnDefinition = "json")
    private String mapDataJson;

    @Transient // Ignore field
    private MapData mapData;

    public MapData getMapData() {
        if (this.mapData == null && this.mapDataJson != null) {
            // Remove backslashes used to escape double quotes
            String correctedJson = this.mapDataJson.replace("\\\"", "\"").trim();

            // Ensure there are no outer quotes surrounding the JSON object
            if (correctedJson.startsWith("\"") && correctedJson.endsWith("\"")) {
                correctedJson = correctedJson.substring(1, correctedJson.length() - 1);
            }

            ObjectMapper mapper = new ObjectMapper();
            try {
                this.mapData = mapper.readValue(correctedJson, MapData.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new ValidationException("Invalid Map Data json");
            }
        }
        return this.mapData;
    }

    public void setMapData(MapData mapData) {
        this.mapData = mapData;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.mapDataJson = mapper.writeValueAsString(mapData);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Map Data json");
        }
    }
}
