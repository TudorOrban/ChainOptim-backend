package org.chainoptim.features.client.model;


import lombok.Builder;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.enums.ShipmentStatus;
import org.chainoptim.shared.enums.TransportType;
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
@Builder
@Entity
@Table(name = "client_shipments")
public class ClientShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "client_order_id", nullable = false)
    private Integer clientOrderId;

    @Column(name = "client_id", nullable = false)
    private Integer clientId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "shipment_starting_date")
    private LocalDateTime shipmentStartingDate;

    @Column(name = "estimated_arrival_date")
    private LocalDateTime estimatedArrivalDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

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

    @Column(name = "src_factory_id")
    private Integer srcFactoryId;

    @Column(name = "src_warehouse_id")
    private Integer srcWarehouseId;

    public ClientShipment deepCopy() {
        return ClientShipment.builder()
//                .id(this.id)
                .clientId(this.clientOrderId)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
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
                .clientId(this.clientId)
                .srcFactoryId(this.srcFactoryId)
                .srcWarehouseId(this.srcWarehouseId)
                .build();
    }
}
