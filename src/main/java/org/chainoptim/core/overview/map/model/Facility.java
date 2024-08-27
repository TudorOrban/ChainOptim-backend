package org.chainoptim.core.overview.map.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facility {

    private Integer id;
    private String name;
    private FacilityType type;
    private Double latitude;
    private Double longitude;
}
