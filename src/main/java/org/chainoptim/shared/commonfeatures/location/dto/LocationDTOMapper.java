package org.chainoptim.shared.commonfeatures.location.dto;

import org.chainoptim.shared.commonfeatures.location.model.Location;

import static org.chainoptim.shared.util.ReflectionUtil.setPropertyWithNullCheck;

public class LocationDTOMapper {

    private LocationDTOMapper() {}

    public static Location convertCreateLocationDTOToLocation(CreateLocationDTO locationDTO) {
        Location location = new Location();
        location.setAddress(locationDTO.getAddress());
        location.setCity(locationDTO.getCity());
        location.setState(locationDTO.getState());
        location.setCountry(locationDTO.getCountry());
        location.setZipCode(locationDTO.getZipCode());
        location.setLatitude(locationDTO.getLatitude());
        location.setLongitude(locationDTO.getLongitude());
        location.setOrganizationId(locationDTO.getOrganizationId());

        return location;
    }

    public static Location updateLocationFromUpdateLocationDTO(Location location, UpdateLocationDTO locationDTO) {
        setPropertyWithNullCheck(location, "address", locationDTO.getAddress());
        setPropertyWithNullCheck(location, "city", locationDTO.getCity());
        setPropertyWithNullCheck(location, "state", locationDTO.getState());
        setPropertyWithNullCheck(location, "country", locationDTO.getCountry());
        setPropertyWithNullCheck(location, "zipCode", locationDTO.getZipCode());
        setPropertyWithNullCheck(location, "latitude", locationDTO.getLatitude());
        setPropertyWithNullCheck(location, "longitude", locationDTO.getLongitude());

        return location;
    }

}
