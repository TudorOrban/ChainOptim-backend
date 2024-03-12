package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.model.SupplierShipment;

import java.util.List;

public interface SupplierShipmentService {

    SupplierShipment getSupplierShipmentById(Integer shipmentId);
    List<SupplierShipment> getSupplierShipmentBySupplyOrderId(Integer orderId);
}
