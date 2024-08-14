package org.chainoptim.features.product.model;

import org.chainoptim.core.map.model.CustomPair;
import org.chainoptim.core.map.model.FacilityType;
import org.chainoptim.shared.enums.ShipmentStatus;
import org.chainoptim.shared.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportRoute {

    private CustomPair<Double, Double> srcLocation;
    private Integer srcLocationId;
    private Integer srcFacilityId;
    private FacilityType srcFacilityType;
    private String srcFacilityName;

    private CustomPair<Double, Double> destLocation;
    private Integer destLocationId;
    private Integer destFacilityId;
    private FacilityType destFacilityType;
    private String destFacilityName;

    private List<CustomPair<Double, Double>> waypoints;
    private CustomPair<Double, Double> liveLocation;

    private TransportType transportType;
    private ShipmentStatus shipmentStatus;

    private LocalDateTime departureDateTime;
    private LocalDateTime estimatedArrivalDateTime;
    private LocalDateTime arrivalDateTime;

    private List<TransportedEntity> transportedEntities;
}
