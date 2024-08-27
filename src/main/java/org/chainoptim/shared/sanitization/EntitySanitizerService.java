package org.chainoptim.shared.sanitization;

import org.chainoptim.core.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.features.demand.dto.*;
import org.chainoptim.features.production.dto.CreateFactoryDTO;
import org.chainoptim.features.production.dto.CreateFactoryInventoryItemDTO;
import org.chainoptim.features.production.dto.UpdateFactoryDTO;
import org.chainoptim.features.production.dto.UpdateFactoryInventoryItemDTO;
import org.chainoptim.features.goods.product.dto.CreateProductDTO;
import org.chainoptim.features.goods.controller.CreateUnitOfMeasurementDTO;
import org.chainoptim.features.goods.product.dto.UpdateProductDTO;
import org.chainoptim.features.goods.controller.UpdateUnitOfMeasurementDTO;
import org.chainoptim.features.goods.component.dto.CreateComponentDTO;
import org.chainoptim.features.goods.stage.dto.CreateStageDTO;
import org.chainoptim.features.goods.controller.UpdateComponentDTO;
import org.chainoptim.features.goods.stage.dto.UpdateStageDTO;
import org.chainoptim.features.supply.dto.*;
import org.chainoptim.features.storage.dto.CreateWarehouseDTO;
import org.chainoptim.features.storage.dto.CreateWarehouseInventoryItemDTO;
import org.chainoptim.features.storage.dto.UpdateWarehouseDTO;
import org.chainoptim.features.storage.dto.UpdateWarehouseInventoryItemDTO;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.dto.UpdateLocationDTO;

public interface EntitySanitizerService {

    // Product
    CreateProductDTO sanitizeCreateProductDTO(CreateProductDTO productDTO);
    UpdateProductDTO sanitizeUpdateProductDTO(UpdateProductDTO productDTO);

    // Product pipeline
    CreateStageDTO sanitizeCreateStageDTO(CreateStageDTO stageDTO);
    UpdateStageDTO sanitizeUpdateStageDTO(UpdateStageDTO stageDTO);

    // Factory
    CreateFactoryDTO sanitizeCreateFactoryDTO(CreateFactoryDTO factoryDTO);
    UpdateFactoryDTO sanitizeUpdateFactoryDTO(UpdateFactoryDTO factoryDTO);
    CreateFactoryInventoryItemDTO sanitizeCreateFactoryInventoryItemDTO(CreateFactoryInventoryItemDTO itemDTO);
    UpdateFactoryInventoryItemDTO sanitizeUpdateFactoryInventoryItemDTO(UpdateFactoryInventoryItemDTO itemDTO);

    // Warehouse
    CreateWarehouseDTO sanitizeCreateWarehouseDTO(CreateWarehouseDTO warehouseDTO);
    UpdateWarehouseDTO sanitizeUpdateWarehouseDTO(UpdateWarehouseDTO warehouseDTO);
    CreateWarehouseInventoryItemDTO sanitizeCreateWarehouseInventoryItemDTO(CreateWarehouseInventoryItemDTO itemDTO);
    UpdateWarehouseInventoryItemDTO sanitizeUpdateWarehouseInventoryItemDTO(UpdateWarehouseInventoryItemDTO itemDTO);

    // Supplier
    CreateSupplierDTO sanitizeCreateSupplierDTO(CreateSupplierDTO supplierDTO);
    UpdateSupplierDTO sanitizeUpdateSupplierDTO(UpdateSupplierDTO supplierDTO);
    CreateSupplierOrderDTO sanitizeCreateSupplierOrderDTO(CreateSupplierOrderDTO orderDTO);
    UpdateSupplierOrderDTO sanitizeUpdateSupplierOrderDTO(UpdateSupplierOrderDTO orderDTO);
    CreateSupplierShipmentDTO sanitizeCreateSupplierShipmentDTO(CreateSupplierShipmentDTO shipmentDTO);
    UpdateSupplierShipmentDTO sanitizeUpdateSupplierShipmentDTO(UpdateSupplierShipmentDTO shipmentDTO);

    // Client
    CreateClientDTO sanitizeCreateClientDTO(CreateClientDTO supplierDTO);
    UpdateClientDTO sanitizeUpdateClientDTO(UpdateClientDTO supplierDTO);
    CreateClientOrderDTO sanitizeCreateClientOrderDTO(CreateClientOrderDTO orderDTO);
    UpdateClientOrderDTO sanitizeUpdateClientOrderDTO(UpdateClientOrderDTO orderDTO);
    CreateClientShipmentDTO sanitizeCreateClientShipmentDTO(CreateClientShipmentDTO shipmentDTO);
    UpdateClientShipmentDTO sanitizeUpdateClientShipmentDTO(UpdateClientShipmentDTO shipmentDTO);

    // Location
    CreateLocationDTO sanitizeCreateLocationDTO(CreateLocationDTO locationDTO);
    UpdateLocationDTO sanitizeUpdateLocationDTO(UpdateLocationDTO locationDTO);

    // Unit of Measurement
    CreateUnitOfMeasurementDTO sanitizeCreateUnitOfMeasurementDTO(CreateUnitOfMeasurementDTO uomDTO);
    UpdateUnitOfMeasurementDTO sanitizeUpdateUnitOfMeasurementDTO(UpdateUnitOfMeasurementDTO uomDTO);

    // Component
    CreateComponentDTO sanitizeCreateComponentDTO(CreateComponentDTO componentDTO);
    UpdateComponentDTO sanitizeUpdateComponentDTO(UpdateComponentDTO componentDTO);

    // Settings
    CreateUserSettingsDTO sanitizeCreateUserSettingsDTO(CreateUserSettingsDTO userSettingsDTO);
    UpdateUserSettingsDTO sanitizeUpdateUserSettingsDTO(UpdateUserSettingsDTO userSettingsDTO);
}
