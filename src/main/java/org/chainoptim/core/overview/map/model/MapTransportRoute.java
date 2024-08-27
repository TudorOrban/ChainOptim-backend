package org.chainoptim.core.overview.map.model;

import org.chainoptim.shared.enums.Feature;
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
public class MapTransportRoute {

    private Integer entityId;
    private Feature entityType;

    private CustomPair<Double, Double> srcLocation;
    private Integer srcFacilityId;
    private FacilityType srcFacilityType;

    private CustomPair<Double, Double> destLocation;
    private Integer destFacilityId;
    private FacilityType destFacilityType;

    private List<CustomPair<Double, Double>> waypoints;
    private CustomPair<Double, Double> liveLocation;

    private TransportType transportType;
    private ShipmentStatus shipmentStatus;

    private LocalDateTime departureDateTime;
    private LocalDateTime estimatedArrivalDateTime;
    private LocalDateTime arrivalDateTime;
}
