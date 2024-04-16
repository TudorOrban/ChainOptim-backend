package org.chainoptim.features.supplier.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.supplier.dto.CreateSupplierShipmentDTO;
import org.chainoptim.features.supplier.dto.SupplierDTOMapper;
import org.chainoptim.features.supplier.dto.UpdateSupplierShipmentDTO;
import org.chainoptim.features.supplier.model.SupplierShipment;
import org.chainoptim.features.supplier.repository.SupplierShipmentRepository;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.repository.LocationRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierShipmentServiceImpl implements SupplierShipmentService {

    private final SupplierShipmentRepository supplierShipmentRepository;
    private final LocationRepository locationRepository;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public SupplierShipmentServiceImpl(SupplierShipmentRepository supplierShipmentRepository,
                                       LocationRepository locationRepository,
                                       SubscriptionPlanLimiterService planLimiterService,
                                       EntitySanitizerService entitySanitizerService) {
        this.supplierShipmentRepository = supplierShipmentRepository;
        this.locationRepository = locationRepository;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    public List<SupplierShipment> getSupplierShipmentBySupplierOrderId(Integer orderId) {
        return supplierShipmentRepository.findBySupplyOrderId(orderId);
    }

    public PaginatedResults<SupplierShipment> getSupplierShipmentsBySupplierOrderIdAdvanced(Integer supplierOrderId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        return supplierShipmentRepository.findBySupplierOrderIdAdvanced(supplierOrderId, searchQuery, sortBy, ascending, page, itemsPerPage);
    }

    public SupplierShipment getSupplierShipmentById(Integer shipmentId) {
        return supplierShipmentRepository.findById(shipmentId).orElseThrow(() -> new ResourceNotFoundException("Supplier shipment with ID: " + shipmentId + " not found."));
    }

    // Create
    public SupplierShipment createSupplierShipment(CreateSupplierShipmentDTO shipmentDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(shipmentDTO.getOrganizationId(), Feature.SUPPLIER_SHIPMENT, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Supplier Shipments for the current Subscription Plan.");
        }

        // Sanitize input and map to entity
        CreateSupplierShipmentDTO sanitizedShipmentDTO = entitySanitizerService.sanitizeCreateSupplierShipmentDTO(shipmentDTO);
        SupplierShipment supplierShipment = SupplierDTOMapper.mapCreateSupplierShipmentDTOTOShipment(sanitizedShipmentDTO);

        if (sanitizedShipmentDTO.getSourceLocationId() != null) {
            Location sourceLocation = locationRepository.findById(sanitizedShipmentDTO.getSourceLocationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Source location with ID: " + sanitizedShipmentDTO.getSourceLocationId() + " not found"));
            supplierShipment.setSourceLocation(sourceLocation);
        }

        if (sanitizedShipmentDTO.getDestinationLocationId() != null) {
            Location destinationLocation = locationRepository.findById(sanitizedShipmentDTO.getDestinationLocationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Destination location with ID: " + sanitizedShipmentDTO.getDestinationLocationId() + " not found"));
            supplierShipment.setDestinationLocation(destinationLocation);
        }

        return supplierShipmentRepository.save(supplierShipment);
    }

    @Transactional
    public List<SupplierShipment> createSupplierShipmentsInBulk(List<CreateSupplierShipmentDTO> shipmentDTOs) {
        // Ensure same organizationId
        if (shipmentDTOs.stream().map(CreateSupplierShipmentDTO::getOrganizationId).distinct().count() > 1) {
            throw new ValidationException("All shipments must belong to the same organization.");
        }
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(shipmentDTOs.getFirst().getOrganizationId(), Feature.SUPPLIER_SHIPMENT, shipmentDTOs.size())) {
            throw new PlanLimitReachedException("You have reached the limit of allowed Supplier Shipments for the current Subscription Plan.");
        }

        // Sanitize and map to entity
        List<SupplierShipment> shipments = shipmentDTOs.stream()
                .map(shipmentDTO -> {
                    CreateSupplierShipmentDTO sanitizedShipmentDTO = entitySanitizerService.sanitizeCreateSupplierShipmentDTO(shipmentDTO);
                    return SupplierDTOMapper.mapCreateSupplierShipmentDTOTOShipment(sanitizedShipmentDTO);
                })
                .toList();

        return supplierShipmentRepository.saveAll(shipments);
    }

    @Transactional
    public List<SupplierShipment> updateSupplierShipmentsInBulk(List<UpdateSupplierShipmentDTO> shipmentDTOs) {
        List<SupplierShipment> shipments = new ArrayList<>();
        for (UpdateSupplierShipmentDTO shipmentDTO : shipmentDTOs) {
            SupplierShipment shipment = supplierShipmentRepository.findById(shipmentDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier Shipment with ID: " + shipmentDTO.getId() + " not found."));

            SupplierDTOMapper.setUpdateSupplierShipmentDTOToSupplierShipment(shipment, shipmentDTO);
            shipments.add(shipment);
        }

        return supplierShipmentRepository.saveAll(shipments);
    }

    @Transactional
    public List<Integer> deleteSupplierShipmentsInBulk(List<Integer> shipmentIds) {
        List<SupplierShipment> shipments = supplierShipmentRepository.findAllById(shipmentIds);

        supplierShipmentRepository.deleteAll(shipments);

        return shipmentIds;
    }
}
