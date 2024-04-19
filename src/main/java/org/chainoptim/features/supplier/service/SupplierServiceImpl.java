package org.chainoptim.features.supplier.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.supplier.dto.CreateSupplierDTO;
import org.chainoptim.features.supplier.dto.SupplierDTOMapper;
import org.chainoptim.features.supplier.dto.SuppliersSearchDTO;
import org.chainoptim.features.supplier.dto.UpdateSupplierDTO;
import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.features.supplier.repository.SupplierRepository;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.service.LocationService;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final LocationService locationService;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository,
                               LocationService locationService,
                               SubscriptionPlanLimiterService planLimiterService,
                               EntitySanitizerService entitySanitizerService) {
        this.supplierRepository = supplierRepository;
        this.locationService = locationService;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Integer supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID: " + supplierId + " not found."));
    }

    public List<Supplier> getSuppliersByOrganizationId(Integer organizationId) {
        return supplierRepository.findByOrganizationId(organizationId);
    }

    public PaginatedResults<SuppliersSearchDTO> getSuppliersByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        PaginatedResults<Supplier> paginatedResults = supplierRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return new PaginatedResults<>(
            paginatedResults.results.stream()
            .map(SupplierDTOMapper::convertToSuppliersSearchDTO)
            .toList(),
            paginatedResults.totalCount
        );
    }

    public Supplier createSupplier(CreateSupplierDTO supplierDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(supplierDTO.getOrganizationId(), Feature.SUPPLIER, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed suppliers for the current Subscription Plan.");
        }

        // Sanitize input
        CreateSupplierDTO sanitizedSupplierDTO = entitySanitizerService.sanitizeCreateSupplierDTO(supplierDTO);

        // Create location if requested
        if (sanitizedSupplierDTO.isCreateLocation() && sanitizedSupplierDTO.getLocation() != null) {
            Location location = locationService.createLocation(sanitizedSupplierDTO.getLocation());
            Supplier supplier = SupplierDTOMapper.convertCreateSupplierDTOToSupplier(sanitizedSupplierDTO);
            supplier.setLocation(location);
            return supplierRepository.save(supplier);
        } else {
            return supplierRepository.save(SupplierDTOMapper.convertCreateSupplierDTOToSupplier(sanitizedSupplierDTO));
        }
    }

    public Supplier updateSupplier(UpdateSupplierDTO supplierDTO) {
        UpdateSupplierDTO sanitizedSupplierDTO = entitySanitizerService.sanitizeUpdateSupplierDTO(supplierDTO);

        Supplier supplier = supplierRepository.findById(sanitizedSupplierDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID: " + sanitizedSupplierDTO.getId() + " not found."));

        supplier.setName(sanitizedSupplierDTO.getName());

        // Create new supplier or use existing or throw if not provided
        Location location;
        if (sanitizedSupplierDTO.isCreateLocation() && sanitizedSupplierDTO.getLocation() != null) {
            location = locationService.createLocation(sanitizedSupplierDTO.getLocation());
        } else if (sanitizedSupplierDTO.getLocationId() != null) {
            location = new Location();
            location.setId(sanitizedSupplierDTO.getLocationId());
        } else {
            throw new ValidationException("Location is required.");
        }
        supplier.setLocation(location);

        supplierRepository.save(supplier);
        return supplier;
    }

    public void deleteSupplier(Integer supplierId) {
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        supplierRepository.delete(supplier);
    }
}
