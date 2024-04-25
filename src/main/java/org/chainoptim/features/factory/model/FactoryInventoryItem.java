package org.chainoptim.features.factory.model;

import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.productpipeline.model.Component;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factory_inventory_items")
public class FactoryInventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "factory_id", nullable = false)
    private Integer factoryId;

    @Column(name = "organization_id")
    private Integer organizationId;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "minimum_required_quantity")
    private Float minimumRequiredQuantity;

    @Column(name = "company_id")
    private String companyId;
}
