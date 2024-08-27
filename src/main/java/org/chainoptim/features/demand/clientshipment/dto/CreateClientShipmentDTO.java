package org.chainoptim.features.demand.clientshipment.dto;

import org.chainoptim.shared.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientShipmentDTO {

    private Integer organizationId;
    private Integer clientId;
    private Integer clientOrderId;
    private Float quantity;
    private LocalDateTime shipmentStartingDate;
    private LocalDateTime estimatedArrivalDate;
    private LocalDateTime arrivalDate;
    private ShipmentStatus status;
    private Integer sourceLocationId;
    private Integer destinationLocationId;
    private Double currentLocationLatitude;
    private Double currentLocationLongitude;
}
