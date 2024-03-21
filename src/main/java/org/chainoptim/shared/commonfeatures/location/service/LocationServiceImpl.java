package org.chainoptim.shared.commonfeatures.location.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.dto.LocationDTOMapper;
import org.chainoptim.shared.commonfeatures.location.dto.UpdateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Fetch
    public List<Location> getLocationsByOrganizationId(Integer organizationId) {
        return locationRepository.findLocationsByOrganizationId(organizationId);
    }

    // Create
    public Location createLocation(CreateLocationDTO locationDTO) {
        return locationRepository.save(LocationDTOMapper.convertCreateLocationDTOToLocation(locationDTO));
    }

    // Update
    public Location updateLocation(UpdateLocationDTO locationDTO) {
        Location location = locationRepository.findById(locationDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Location with ID: " + locationDTO.getId() + " not found."));
        Location location1 = LocationDTOMapper.updateLocationFromUpdateLocationDTO(location, locationDTO);
        return locationRepository.save(location1);
    }

    // Delete
    public void deleteLocation(Integer locationId) {
        locationRepository.deleteById(locationId);
    }
}