package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.dto.CreateSupplierShipmentDTO;
import org.chainoptim.features.supplier.dto.UpdateSupplierShipmentDTO;
import org.chainoptim.features.supplier.model.SupplierShipment;

import java.util.List;

public interface SupplierShipmentService {

    List<SupplierShipment> getSupplierShipmentBySupplierOrderId(Integer orderId);
    SupplierShipment getSupplierShipmentById(Integer shipmentId);
    SupplierShipment createSupplierShipment(CreateSupplierShipmentDTO shipmentDTO);
    List<SupplierShipment> createSupplierShipmentsInBulk(List<CreateSupplierShipmentDTO> shipmentDTOs);
    List<SupplierShipment> updateSupplierShipmentsInBulk(List<UpdateSupplierShipmentDTO> shipmentDTOs);
}
