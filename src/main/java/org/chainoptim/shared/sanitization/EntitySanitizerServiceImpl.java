package org.chainoptim.shared.sanitization;

import org.chainoptim.features.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.factory.dto.CreateFactoryInventoryItemDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.factory.dto.UpdateFactoryInventoryItemDTO;
import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.productpipeline.dto.CreateStageDTO;
import org.chainoptim.features.productpipeline.dto.UpdateStageDTO;
import org.chainoptim.features.supply.dto.CreateSupplierDTO;
import org.chainoptim.features.supply.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supply.dto.UpdateSupplierDTO;
import org.chainoptim.features.warehouse.dto.CreateWarehouseDTO;
import org.chainoptim.features.warehouse.dto.UpdateWarehouseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntitySanitizerServiceImpl implements EntitySanitizerService {

    private final SanitizationService sanitizationService;

    @Autowired
    public EntitySanitizerServiceImpl(SanitizationService sanitizationService) {
        this.sanitizationService = sanitizationService;
    }

    // Products
    public CreateProductDTO sanitizeCreateProductDTO(CreateProductDTO productDTO) {
        productDTO.setName(sanitizationService.sanitize(productDTO.getName()));
        productDTO.setDescription(sanitizationService.sanitize(productDTO.getDescription()));

        return productDTO;
    }

    public UpdateProductDTO sanitizeUpdateProductDTO(UpdateProductDTO productDTO) {
        productDTO.setName(sanitizationService.sanitize(productDTO.getName()));
        productDTO.setDescription(sanitizationService.sanitize(productDTO.getDescription()));

        return productDTO;
    }

    // Product pipeline
    public CreateStageDTO sanitizeCreateStageDTO(CreateStageDTO stageDTO) {
        stageDTO.setName(sanitizationService.sanitize(stageDTO.getName()));
        stageDTO.setDescription(sanitizationService.sanitize(stageDTO.getDescription()));

        return stageDTO;
    }

    public UpdateStageDTO sanitizeUpdateStageDTO(UpdateStageDTO stageDTO) {
        stageDTO.setName(sanitizationService.sanitize(stageDTO.getName()));
        stageDTO.setDescription(sanitizationService.sanitize(stageDTO.getDescription()));

        return stageDTO;
    }

    // Factories
    public CreateFactoryDTO sanitizeCreateFactoryDTO(CreateFactoryDTO factoryDTO) {
        factoryDTO.setName(sanitizationService.sanitize(factoryDTO.getName()));

        return factoryDTO;
    }

    public UpdateFactoryDTO sanitizeUpdateFactoryDTO(UpdateFactoryDTO factoryDTO) {
        factoryDTO.setName(sanitizationService.sanitize(factoryDTO.getName()));

        return factoryDTO;
    }

    public CreateFactoryInventoryItemDTO sanitizeCreateFactoryInventoryItemDTO(CreateFactoryInventoryItemDTO itemDTO) {
        return itemDTO;
    }

    public UpdateFactoryInventoryItemDTO sanitizeUpdateFactoryInventoryItemDTO(UpdateFactoryInventoryItemDTO itemDTO) {
        return itemDTO;
    }

    // Suppliers
    public CreateSupplierDTO sanitizeCreateSupplierDTO(CreateSupplierDTO supplierDTO) {
        supplierDTO.setName(sanitizationService.sanitize(supplierDTO.getName()));

        return supplierDTO;
    }

    public UpdateSupplierDTO sanitizeUpdateSupplierDTO(UpdateSupplierDTO supplierDTO) {
        supplierDTO.setName(sanitizationService.sanitize(supplierDTO.getName()));

        return supplierDTO;
    }

    public CreateSupplierOrderDTO sanitizeCreateSupplierOrderDTO(CreateSupplierOrderDTO orderDTO) {
        return orderDTO;
    }

    // Warehouses
    public CreateWarehouseDTO sanitizeCreateWarehouseDTO(CreateWarehouseDTO warehouseDTO) {
        warehouseDTO.setName(sanitizationService.sanitize(warehouseDTO.getName()));

        return warehouseDTO;
    }

    public UpdateWarehouseDTO sanitizeUpdateWarehouseDTO(UpdateWarehouseDTO warehouseDTO) {
        warehouseDTO.setName(sanitizationService.sanitize(warehouseDTO.getName()));

        return warehouseDTO;
    }
}
