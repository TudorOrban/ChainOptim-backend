package org.chainoptim.features.client.model;


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
@Table(name = "client_shipments")
public class ClientShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_order_id", nullable = false)
    private Integer clientOrderId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "shipment_starting_date")
    private LocalDateTime shipmentStartingDate;

    @Column(name = "estimated_arrival_date")
    private LocalDateTime estimatedArrivalDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Column(name = "transporter_type")
    private String transporterType;

    @Column(name = "status")
    private String status;

    @Column(name = "source_location_id")
    private Integer sourceLocationId;

    @Column(name = "destination_location_id")
    private Integer destinationLocationId;

    @Column(name = "current_location_latitude")
    private Float currentLocationLatitude;

    @Column(name = "current_location_longitude")
    private Float currentLocationLongitude;
}
