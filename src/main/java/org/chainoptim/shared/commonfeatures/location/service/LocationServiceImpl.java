package org.chainoptim.shared.commonfeatures.location.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.dto.LocationDTOMapper;
import org.chainoptim.shared.commonfeatures.location.dto.UpdateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final GeocodingService geocodingService;
    private final EntitySanitizerService sanitizerService;

    @Value("${app.environment}")
    private String environment;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository,
                               GeocodingService geocodingService,
                               EntitySanitizerService sanitizerService) {
        this.locationRepository = locationRepository;
        this.geocodingService = geocodingService;
        this.sanitizerService = sanitizerService;
    }

    // Fetch
    public List<Location> getLocationsByOrganizationId(Integer organizationId) {
        return locationRepository.findLocationsByOrganizationId(organizationId);
    }

    // Create
    public Location createLocation(CreateLocationDTO locationDTO) {
        CreateLocationDTO sanitizedDTO = sanitizerService.sanitizeCreateLocationDTO(locationDTO);

        if (!environment.equals("prod")) {
            return locationRepository.save(LocationDTOMapper.convertCreateLocationDTOToLocation(sanitizedDTO));
        }

        // Only use geocoding in production
        String fullAddress = Stream.of(sanitizedDTO.getAddress(), sanitizedDTO.getCity(), sanitizedDTO.getState(), sanitizedDTO.getCountry(), sanitizedDTO.getZipCode())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));

        if (sanitizedDTO.isUseGeocoding()) {
            Double[] coordinates = geocodingService.getCoordinates(fullAddress);
            if (coordinates != null && coordinates.length == 2) {
                sanitizedDTO.setLatitude(coordinates[0]);
                sanitizedDTO.setLongitude(coordinates[1]);
            }
        }

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