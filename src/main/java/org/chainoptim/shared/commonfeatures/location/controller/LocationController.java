package org.chainoptim.shared.commonfeatures.location.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.dto.UpdateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.service.LocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;
    private final SecurityService securityService;

    @Autowired
    public LocationController(LocationService locationService,
                              SecurityService securityService) {
        this.locationService = locationService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Location\", \"Read\")")
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<Location>> getLocationsByOrganizationId(@PathVariable("organizationId") Integer organizationId) {
        List<Location> locations = locationService.getLocationsByOrganizationId(organizationId);
        return ResponseEntity.ok(locations);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#locationDTO.getOrganizationId(), \"Location\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<Location> createLocation(@RequestBody CreateLocationDTO locationDTO) {
        Location newLocation = locationService.createLocation(locationDTO);
        return ResponseEntity.ok(newLocation);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#locationDTO.getId(), \"Location\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<Location> updateLocation(@RequestBody UpdateLocationDTO locationDTO) {
        Location updatedLocation = locationService.updateLocation(locationDTO);
        return ResponseEntity.ok(updatedLocation);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#locationId, \"Location\", \"Delete\")")
    @DeleteMapping("/delete/{locationId}")
    public ResponseEntity<Void> deleteLocation(@PathVariable("locationId") Integer locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.ok().build();
    }
}
