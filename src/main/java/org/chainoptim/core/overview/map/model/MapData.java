package org.chainoptim.core.overview.map.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapData {

    private List<Facility> facilities;
    private List<MapTransportRoute> transportRoutes;
}
