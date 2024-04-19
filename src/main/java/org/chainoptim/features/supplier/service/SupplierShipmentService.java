package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.dto.CreateSupplierShipmentDTO;
import org.chainoptim.features.supplier.dto.UpdateSupplierShipmentDTO;
import org.chainoptim.features.supplier.model.SupplierShipment;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface SupplierShipmentService {

    List<SupplierShipment> getSupplierShipmentBySupplierOrderId(Integer orderId);
    PaginatedResults<SupplierShipment> getSupplierShipmentsBySupplierOrderIdAdvanced(Integer supplierOrderId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
    SupplierShipment getSupplierShipmentById(Integer shipmentId);
    SupplierShipment createSupplierShipment(CreateSupplierShipmentDTO shipmentDTO);
    List<SupplierShipment> createSupplierShipmentsInBulk(List<CreateSupplierShipmentDTO> shipmentDTOs);
    List<SupplierShipment> updateSupplierShipmentsInBulk(List<UpdateSupplierShipmentDTO> shipmentDTOs);
    List<Integer> deleteSupplierShipmentsInBulk(List<Integer> shipmentIds);
}
