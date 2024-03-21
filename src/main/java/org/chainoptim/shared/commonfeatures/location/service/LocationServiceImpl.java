package org.chainoptim.shared.commonfeatures.location.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.dto.LocationDTOMapper;
import org.chainoptim.shared.commonfeatures.location.dto.UpdateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final EntitySanitizerService sanitizerService;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository,
                               EntitySanitizerService sanitizerService) {
        this.locationRepository = locationRepository;
        this.sanitizerService = sanitizerService;
    }

    // Fetch
    public List<Location> getLocationsByOrganizationId(Integer organizationId) {
        return locationRepository.findLocationsByOrganizationId(organizationId);
    }

    // Create
    public Location createLocation(CreateLocationDTO locationDTO) {
        CreateLocationDTO sanitizedDTO = sanitizerService.sanitizeCreateLocationDTO(locationDTO);
        return locationRepository.save(LocationDTOMapper.convertCreateLocationDTOToLocation(sanitizedDTO));
    }

    // Update
    public Location updateLocation(UpdateLocationDTO locationDTO) {
        UpdateLocationDTO sanitizedDTO = sanitizerService.sanitizeUpdateLocationDTO(locationDTO);
        Location location = locationRepository.findById(sanitizedDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Location with ID: " + sanitizedDTO.getId() + " not found."));
        Location location1 = LocationDTOMapper.updateLocationFromUpdateLocationDTO(location, sanitizedDTO);
        return locationRepository.save(location1);
    }

    // Delete
    public void deleteLocation(Integer locationId) {
        locationRepository.deleteById(locationId);
    }
}