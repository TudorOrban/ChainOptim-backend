package org.chainoptim.shared.commonfeatures.location.service;

import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.dto.UpdateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.model.Location;

import java.util.List;

public interface LocationService {

    List<Location> getLocationsByOrganizationId(Integer organizationId);
    Location createLocation(CreateLocationDTO locationDTO);
    Location updateLocation(UpdateLocationDTO locationDTO);
    void deleteLocation(Integer locationId);
}
