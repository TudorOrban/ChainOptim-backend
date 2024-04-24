package org.chainoptim.features.supplier.service;

import org.chainoptim.features.supplier.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supplier.dto.UpdateSupplierOrderDTO;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;
import java.util.Map;

public interface SupplierOrderService {

    List<SupplierOrder> getSupplierOrdersByOrganizationId(Integer organizationId);
    List<SupplierOrder> getSupplierOrdersBySupplierId(Integer supplierId);
    PaginatedResults<SupplierOrder> getSupplierOrdersBySupplierIdAdvanced(Integer supplierId, String searchQuery, String filtersJson, String sortBy, boolean ascending, int page, int itemsPerPage);

    SupplierOrder createSupplierOrder(CreateSupplierOrderDTO order);
    List<SupplierOrder> createSupplierOrdersInBulk(List<CreateSupplierOrderDTO> orderDTOs);
    List<SupplierOrder> updateSuppliersOrdersInBulk(List<UpdateSupplierOrderDTO> orderDTOs);
    List<Integer> deleteSupplierOrdersInBulk(List<Integer> orders);
}
