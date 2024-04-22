package org.chainoptim.shared.sanitization;

import org.chainoptim.core.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.features.client.dto.*;
import org.chainoptim.features.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.factory.dto.CreateFactoryInventoryItemDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryInventoryItemDTO;
import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.CreateUnitOfMeasurementDTO;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.product.dto.UpdateUnitOfMeasurementDTO;
import org.chainoptim.features.productpipeline.dto.CreateComponentDTO;
import org.chainoptim.features.productpipeline.dto.CreateStageDTO;
import org.chainoptim.features.productpipeline.dto.UpdateComponentDTO;
import org.chainoptim.features.productpipeline.dto.UpdateStageDTO;
import org.chainoptim.features.supplier.dto.*;
import org.chainoptim.features.warehouse.dto.CreateWarehouseDTO;
import org.chainoptim.features.warehouse.dto.CreateWarehouseInventoryItemDTO;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseDTO;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseInventoryItemDTO;
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
    CreateSupplierShipmentDTO sanitizeCreateSupplierShipmentDTO(CreateSupplierShipmentDTO shipmentDTO);
    UpdateSupplierShipmentDTO sanitizeUpdateSupplierShipmentDTO(UpdateSupplierShipmentDTO shipmentDTO);

    // Client
    CreateClientDTO sanitizeCreateClientDTO(CreateClientDTO supplierDTO);
    UpdateClientDTO sanitizeUpdateClientDTO(UpdateClientDTO supplierDTO);
    CreateClientOrderDTO sanitizeCreateClientOrderDTO(CreateClientOrderDTO orderDTO);
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
