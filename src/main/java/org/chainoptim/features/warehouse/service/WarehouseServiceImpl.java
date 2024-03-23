package org.chainoptim.features.warehouse.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.warehouse.dto.CreateWarehouseDTO;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseDTO;
import org.chainoptim.features.warehouse.dto.WarehouseDTOMapper;
import org.chainoptim.features.warehouse.dto.WarehousesSearchDTO;
import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.repository.WarehouseRepository;

import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.service.LocationService;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final LocationService locationService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository,
                                LocationService locationService,
                                EntitySanitizerService entitySanitizerService) {
        this.warehouseRepository = warehouseRepository;
        this.locationService = locationService;
        this.entitySanitizerService = entitySanitizerService;
    }


    public Warehouse getWarehouseById(Integer warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse with ID: " + warehouseId + " not found."));
    }

    public List<Warehouse> getWarehousesByOrganizationId(Integer organizationId) {
        return warehouseRepository.findByOrganizationId(organizationId);
    }

    public PaginatedResults<WarehousesSearchDTO> getWarehousesByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        PaginatedResults<Warehouse> paginatedResults = warehouseRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return new PaginatedResults<>(
                paginatedResults.results.stream()
                        .map(WarehouseDTOMapper::convertToWarehousesSearchDTO)
                        .toList(),
                paginatedResults.totalCount
        );
    }

    public Warehouse createWarehouse(CreateWarehouseDTO warehouseDTO) {
        CreateWarehouseDTO sanitizedWarehouseDTO = entitySanitizerService.sanitizeCreateWarehouseDTO(warehouseDTO);

        // Create location if requested
        if (sanitizedWarehouseDTO.isCreateLocation() && sanitizedWarehouseDTO.getLocation() != null) {
            Location location = locationService.createLocation(sanitizedWarehouseDTO.getLocation());
            Warehouse warehouse = WarehouseDTOMapper.convertCreateWarehouseDTOToWarehouse(sanitizedWarehouseDTO);
            warehouse.setLocation(location);
            return warehouseRepository.save(warehouse);
        } else {
            return warehouseRepository.save(WarehouseDTOMapper.convertCreateWarehouseDTOToWarehouse(sanitizedWarehouseDTO));
        }
    }

    public Warehouse updateWarehouse(UpdateWarehouseDTO warehouseDTO) {
        UpdateWarehouseDTO sanitizedWarehouseDTO = entitySanitizerService.sanitizeUpdateWarehouseDTO(warehouseDTO);
        Warehouse warehouse = warehouseRepository.findById(sanitizedWarehouseDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse with ID: " + sanitizedWarehouseDTO.getId() + " not found."));

        warehouse.setName(sanitizedWarehouseDTO.getName());

        // Create new location or use existing or throw if not provided
        Location location;
        if (sanitizedWarehouseDTO.isCreateLocation() && sanitizedWarehouseDTO.getLocation() != null) {
            location = locationService.createLocation(sanitizedWarehouseDTO.getLocation());
        } else if (sanitizedWarehouseDTO.getLocationId() != null){
            location = new Location();
            location.setId(sanitizedWarehouseDTO.getLocationId());
        } else {
            throw new ValidationException("Location is required.");
        }
        warehouse.setLocation(location);

        warehouseRepository.save(warehouse);
        return warehouse;
    }

    public void deleteWarehouse(Integer warehouseId) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);
        warehouseRepository.delete(warehouse);
    }
}
