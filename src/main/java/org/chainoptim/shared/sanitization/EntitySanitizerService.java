package org.chainoptim.shared.sanitization;

import org.chainoptim.features.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.factory.dto.CreateFactoryInventoryItemDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryInventoryItemDTO;
import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.supply.dto.CreateSupplierDTO;
import org.chainoptim.features.supply.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supply.dto.UpdateSupplierDTO;
import org.chainoptim.features.warehouse.dto.CreateWarehouseDTO;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseDTO;

public interface EntitySanitizerService {

    CreateProductDTO sanitizeCreateProductDTO(CreateProductDTO productDTO);
    UpdateProductDTO sanitizeUpdateProductDTO(UpdateProductDTO productDTO);
    CreateFactoryDTO sanitizeCreateFactoryDTO(CreateFactoryDTO factoryDTO);
    UpdateFactoryDTO sanitizeUpdateFactoryDTO(UpdateFactoryDTO factoryDTO);
    CreateFactoryInventoryItemDTO sanitizeCreateFactoryInventoryItemDTO(CreateFactoryInventoryItemDTO itemDTO);
    UpdateFactoryInventoryItemDTO sanitizeUpdateFactoryInventoryItemDTO(UpdateFactoryInventoryItemDTO itemDTO);
    CreateSupplierDTO sanitizeCreateSupplierDTO(CreateSupplierDTO supplierDTO);
    UpdateSupplierDTO sanitizeUpdateSupplierDTO(UpdateSupplierDTO supplierDTO);
    CreateSupplierOrderDTO sanitizeCreateSupplierOrderDTO(CreateSupplierOrderDTO orderDTO);
    CreateWarehouseDTO sanitizeCreateWarehouseDTO(CreateWarehouseDTO warehouseDTO);
    UpdateWarehouseDTO sanitizeUpdateWarehouseDTO(UpdateWarehouseDTO warehouseDTO);
}
