package org.chainoptim.features.supply.suppliershipment.model;

import jakarta.persistence.*;
import lombok.*;
import org.chainoptim.shared.enums.TransportType;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.enums.ShipmentStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "supplier_shipments")
public class SupplierShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "supplier_order_id", nullable = false)
    private Integer supplierOrderId;

    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "shipment_starting_date")
    private LocalDateTime shipmentStartingDate;

    @Column(name = "estimated_arrival_date")
    private LocalDateTime estimatedArrivalDate;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "transport_type")
    @Enumerated(EnumType.STRING)
    private TransportType transportType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_location_id")
    private Location sourceLocation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_location_id")
    private Location destinationLocation;

    @Column(name = "current_location_latitude")
    private Double currentLocationLatitude;

    @Column(name = "current_location_longitude")
    private Double currentLocationLongitude;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "dest_factory_id")
    private Integer destFactoryId;

    @Column(name = "dest_warehouse_id")
    private Integer destWarehouseId;

    public SupplierShipment deepCopy() {
        return SupplierShipment.builder()
//                .id(this.id)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .supplierOrderId(this.supplierOrderId)
                .quantity(this.quantity)
                .shipmentStartingDate(this.shipmentStartingDate)
                .estimatedArrivalDate(this.estimatedArrivalDate)
                .arrivalDate(this.arrivalDate)
                .transportType(this.transportType)
                .status(this.status)
                .sourceLocation(this.sourceLocation)
                .destinationLocation(this.destinationLocation)
                .currentLocationLatitude(this.currentLocationLatitude)
                .currentLocationLongitude(this.currentLocationLongitude)
                .organizationId(this.organizationId)
                .supplierId(this.supplierId)
                .build();
    }
}
