package org.chainoptim.features.supply.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "supplier_orders")
public class SupplierOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;

    @Column(name = "component_id", nullable = false)
    private Integer componentId;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "order_date", nullable = true)
    private LocalDateTime orderDate;

    @Column(name = "estimated_delivery_date", nullable = true)
    private LocalDateTime estimatedDeliveryDate;

    @Column(name = "delivery_date", nullable = true)
    private LocalDateTime deliveryDate;

    public enum Status {
        Initiated,
        Negociated,
        Placed,
        Delivered
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
}
