package org.chainoptim.features.supply.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.supply.model.SupplierShipment;
import org.chainoptim.features.supply.repository.SupplierShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierShipmentServiceImpl implements SupplierShipmentService {

    private final SupplierShipmentRepository supplierShipmentRepository;

    @Autowired
    public SupplierShipmentServiceImpl(SupplierShipmentRepository supplierShipmentRepository) {
        this.supplierShipmentRepository = supplierShipmentRepository;
    }

    public SupplierShipment getSupplierShipmentById(Integer shipmentId) {
        return supplierShipmentRepository.findById(shipmentId).orElseThrow(() -> new ResourceNotFoundException("Supplier shipment with ID: " + shipmentId + " not found."));
    }

    public List<SupplierShipment> getSupplierShipmentBySupplyOrderId(Integer orderId) {
        return supplierShipmentRepository.findBySupplyOrderId(orderId);
    }
}
