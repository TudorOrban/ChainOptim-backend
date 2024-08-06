package org.chainoptim.features.client.dto;

import org.chainoptim.shared.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientShipmentDTO {

    private Integer id;
    private Integer organizationId;
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
