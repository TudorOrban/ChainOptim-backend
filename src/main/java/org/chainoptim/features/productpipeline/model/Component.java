package org.chainoptim.features.productpipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.chainoptim.features.factory.model.FactoryInventoryItem;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "components")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id", nullable = true)
    private UnitOfMeasurement unit;

    @OneToMany(mappedBy = "component", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FactoryInventoryItem> factoryInventoryItems;

    @OneToMany(mappedBy = "component", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WarehouseInventoryItem> warehouseInventoryItems;

    @OneToMany(mappedBy = "component", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StageInput> stageInputs;

    @OneToMany(mappedBy = "component", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StageOutput> stageOutputs;

    @OneToMany(mappedBy = "component", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SupplierOrder> componentSupplierOrders;
}
