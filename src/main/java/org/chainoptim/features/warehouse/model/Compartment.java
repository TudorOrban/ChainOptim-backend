package org.chainoptim.features.warehouse.model;

import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.scanalysis.supply.performance.model.SupplierPerformanceReport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compartments")
public class Compartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "warehouse_id", nullable = false)
    private Integer warehouseId;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    // Manual deserialization and caching of JSON column
    @Column(name = "data_json", columnDefinition = "json")
    private String dataJson;

    @Transient // Ignore field
    private CompartmentData data;

    public CompartmentData getData() {
        if (this.data == null && this.dataJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            try {
                this.data = mapper.readValue(this.dataJson, CompartmentData.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid Compartment json");
            }
        }
        return this.data;
    }

    public void setReport(CompartmentData data) {
        this.data = data;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            this.dataJson = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ValidationException("Invalid Data json");
        }
    }

}
