package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.model.SupplierShipment;

import java.util.List;

public interface SupplierShipmentService {

    SupplierShipment getSupplierShipmentById(Integer shipmentId);
    List<SupplierShipment> getSupplierShipmentBySupplyOrderId(Integer orderId);
}
