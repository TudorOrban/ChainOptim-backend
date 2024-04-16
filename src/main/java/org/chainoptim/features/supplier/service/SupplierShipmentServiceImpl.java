package org.chainoptim.features.supplier.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.supplier.dto.CreateSupplierShipmentDTO;
import org.chainoptim.features.supplier.dto.SupplierDTOMapper;
import org.chainoptim.features.supplier.dto.UpdateSupplierOrderDTO;
import org.chainoptim.features.supplier.dto.UpdateSupplierShipmentDTO;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.features.supplier.model.SupplierShipment;
import org.chainoptim.features.supplier.repository.SupplierShipmentRepository;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierShipmentServiceImpl implements SupplierShipmentService {

    private final SupplierShipmentRepository supplierShipmentRepository;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public SupplierShipmentServiceImpl(SupplierShipmentRepository supplierShipmentRepository,
                                       SubscriptionPlanLimiterService planLimiterService,
                                       EntitySanitizerService entitySanitizerService) {
        this.supplierShipmentRepository = supplierShipmentRepository;
        this.planLimiterService = planLimiterService;
        this.entitySanitizerService = entitySanitizerService;
    }

    public List<SupplierShipment> getSupplierShipmentBySupplierOrderId(Integer orderId) {
        return supplierShipmentRepository.findBySupplyOrderId(orderId);
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
}
