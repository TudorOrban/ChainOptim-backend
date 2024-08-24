package org.chainoptim.features.product.model;

import org.chainoptim.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resource_transport_routes")
public class ResourceTransportRoute {

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

    @Column(name = "company_id")
    private String companyId;

    // Manual deserialization and caching of JSON column
    @Column(name = "transport_route", columnDefinition = "json")
    private String transportRouteJson;

    @Transient // Ignore field
    private TransportRoute transportRoute;

    public TransportRoute getTransportRoute() {
        if (this.transportRoute == null && this.transportRouteJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            try {
                this.transportRoute = mapper.readValue(this.transportRouteJson, TransportRoute.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid Transport Route json");
            }
        }
        return this.transportRoute;
    }

    public void setTransportRoute(TransportRoute transportRoute) {
        this.transportRoute = transportRoute;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            this.transportRouteJson = mapper.writeValueAsString(transportRoute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ValidationException("Invalid Transport Route json");
        }
    }
}
