package org.chainoptim.features.factory.dto;

import lombok.Data;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;

@Data
public class UpdateFactoryDTO {

    private Integer id;
    private String name;
    private Integer locationId;
    private CreateLocationDTO location;
    private boolean createLocation;
}
