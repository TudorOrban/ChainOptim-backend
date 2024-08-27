package org.chainoptim.features.storage.warehouse.service;

import org.chainoptim.core.tenant.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.storage.warehouse.model.Warehouse;
import org.chainoptim.features.storage.warehouse.repository.WarehouseRepository;

import org.chainoptim.features.storage.warehouse.dto.*;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.service.LocationService;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.dto.SmallEntityDTO;
import org.chainoptim.shared.search.model.PaginatedResults;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final LocationService locationService;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository,
                                LocationService locationService,
                                SubscriptionPlanLimiterService planLimiterService,
                                EntitySanitizerService entitySanitizerService) {
        this.warehouseRepository = warehouseRepository;
        this.locationService = locationService;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    // Fetch
    public Warehouse getWarehouseById(Integer warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse with ID: " + warehouseId + " not found."));
    }

    public List<WarehousesSearchDTO> getWarehousesByOrganizationIdSmall(Integer organizationId) {
        return warehouseRepository.findByOrganizationIdSmall(organizationId);
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

    public WarehouseOverviewDTO getWarehouseOverview(Integer warehouseId) {
        List<SmallEntityDTO> compartments = warehouseRepository.findCompartmentsByWarehouseId(warehouseId);
        List<SmallEntityDTO> storedComponents = warehouseRepository.findStoredComponentsByWarehouseId(warehouseId);
        List<SmallEntityDTO> storedProducts = warehouseRepository.findStoredProductsByWarehouseId(warehouseId);
        List<SmallEntityDTO> deliveredFromSuppliers = warehouseRepository.findDeliveredFromSuppliersByWarehouseId(warehouseId);
        List<SmallEntityDTO> deliveredToClients = warehouseRepository.findDeliveredToClientsByWarehouseId(warehouseId);

        return new WarehouseOverviewDTO(compartments, storedComponents, storedProducts, deliveredFromSuppliers, deliveredToClients);
    }

    // Create
    public Warehouse createWarehouse(CreateWarehouseDTO warehouseDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(warehouseDTO.getOrganizationId(), Feature.WAREHOUSE, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed warehouses for the current Subscription Plan.");
        }

        // Sanitize input
        CreateWarehouseDTO sanitizedWarehouseDTO = entitySanitizerService.sanitizeCreateWarehouseDTO(warehouseDTO);

        // Create location if requested
        if (sanitizedWarehouseDTO.isCreateLocation() && sanitizedWarehouseDTO.getLocation() != null) {
            Location location = locationService.createLocation(sanitizedWarehouseDTO.getLocation());
            Warehouse warehouse = WarehouseDTOMapper.mapCreateWarehouseDTOToWarehouse(sanitizedWarehouseDTO);
            warehouse.setLocation(location);
            return warehouseRepository.save(warehouse);
        } else {
            return warehouseRepository.save(WarehouseDTOMapper.mapCreateWarehouseDTOToWarehouse(sanitizedWarehouseDTO));
        }
    }

    // Update
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

    // Delete
    @Transactional
    public void deleteWarehouse(Integer warehouseId) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(warehouseId);
        warehouseRepository.delete(warehouse);
    }
}
