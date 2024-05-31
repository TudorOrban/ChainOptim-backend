package org.chainoptim.features.client.dto;

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
    private Float quantity;
    private LocalDateTime shipmentStartingDate;
    private LocalDateTime estimatedArrivalDate;
    private LocalDateTime arrivalDate;
    private String status;
    private Integer sourceLocationId;
    private Integer destinationLocationId;
    private Float currentLocationLatitude;
    private Float currentLocationLongitude;
}
