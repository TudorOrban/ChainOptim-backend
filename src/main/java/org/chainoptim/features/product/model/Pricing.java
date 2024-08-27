package org.chainoptim.features.product.model;

import org.chainoptim.exception.ValidationException;

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
@Table(name = "pricings")
public class Pricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    // Manual deserialization and caching of JSON column
    @Column(name = "product_pricing", columnDefinition = "json")
    private String pricingJson;

    @Transient // Ignore field
    private ProductPricing productPricing;

    public ProductPricing getProductPricing() {
        if (this.productPricing == null && this.pricingJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            try {
                this.productPricing = mapper.readValue(this.pricingJson, ProductPricing.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid Pricing json");
            }
        }
        return this.productPricing;
    }

    public void setProductPricing(ProductPricing pricing) {
        this.productPricing = pricing;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            this.pricingJson = mapper.writeValueAsString(pricing);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ValidationException("Invalid Pricing json");
        }
    }
}
