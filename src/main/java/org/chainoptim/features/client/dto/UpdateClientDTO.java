package org.chainoptim.features.client.dto;

import lombok.Data;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;

@Data
public class UpdateClientDTO {

    private Integer id;
    private String name;
    private Integer locationId;
    private CreateLocationDTO location;
    private boolean createLocation;
}
