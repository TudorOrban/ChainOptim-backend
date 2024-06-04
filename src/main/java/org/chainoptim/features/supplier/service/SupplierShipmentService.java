package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.dto.CreateSupplierShipmentDTO;
import org.chainoptim.features.supplier.dto.UpdateSupplierShipmentDTO;
import org.chainoptim.features.supplier.model.SupplierShipment;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;

public interface SupplierShipmentService {

    List<SupplierShipment> getSupplierShipmentBySupplierOrderId(Integer orderId);
    PaginatedResults<SupplierShipment> getSupplierShipmentsAdvanced(SearchMode searchMode, Integer entity, SearchParams searchParams);
    SupplierShipment getSupplierShipmentById(Integer shipmentId);
    SupplierShipment createSupplierShipment(CreateSupplierShipmentDTO shipmentDTO);
    List<SupplierShipment> createSupplierShipmentsInBulk(List<CreateSupplierShipmentDTO> shipmentDTOs);
    List<SupplierShipment> updateSupplierShipmentsInBulk(List<UpdateSupplierShipmentDTO> shipmentDTOs);
    List<Integer> deleteSupplierShipmentsInBulk(List<Integer> shipmentIds);
}
