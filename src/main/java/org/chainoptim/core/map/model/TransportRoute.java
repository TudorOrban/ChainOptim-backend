package org.chainoptim.core.map.model;

import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.enums.ShipmentStatus;
import org.chainoptim.shared.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportRoute {

    private Integer entityId;
    private Feature entityType;

    private Pair<Double, Double> srcLocation;
    private Integer srcFacilityId;
    private FacilityType srcFacilityType;

    private Pair<Double, Double> destLocation;
    private Integer destFacilityId;
    private FacilityType destFacilityType;

    private List<Pair<Double, Double>> waypoints;
    private Pair<Double, Double> liveLocation;

    private TransportType transportType;
    private ShipmentStatus shipmentStatus;

    private LocalDateTime departureDateTime;
    private LocalDateTime estimatedArrivalDateTime;
    private LocalDateTime arrivalDateTime;
}
