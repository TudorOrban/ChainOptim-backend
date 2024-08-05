package org.chainoptim.features.supplier.dto;

import org.chainoptim.shared.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSupplierShipmentDTO {

    private Integer organizationId;
    private Integer supplierId;
    private Integer supplierOrderId;
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
