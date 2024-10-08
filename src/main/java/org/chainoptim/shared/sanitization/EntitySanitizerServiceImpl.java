package org.chainoptim.shared.sanitization;

import org.chainoptim.core.tenant.settings.dto.CreateUserSettingsDTO;
import org.chainoptim.core.tenant.settings.dto.UpdateUserSettingsDTO;
import org.chainoptim.features.demand.client.dto.CreateClientDTO;
import org.chainoptim.features.demand.client.dto.UpdateClientDTO;
import org.chainoptim.features.demand.clientorder.dto.CreateClientOrderDTO;
import org.chainoptim.features.demand.clientorder.dto.UpdateClientOrderDTO;
import org.chainoptim.features.demand.clientshipment.dto.CreateClientShipmentDTO;
import org.chainoptim.features.demand.clientshipment.dto.UpdateClientShipmentDTO;
import org.chainoptim.features.supply.suppliershipment.dto.CreateSupplierShipmentDTO;
import org.chainoptim.features.supply.suppliershipment.dto.UpdateSupplierShipmentDTO;
import org.chainoptim.features.production.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.production.inventory.dto.CreateFactoryInventoryItemDTO;
import org.chainoptim.features.production.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.production.inventory.dto.UpdateFactoryInventoryItemDTO;
import org.chainoptim.features.goods.product.dto.CreateProductDTO;
import org.chainoptim.features.goods.product.dto.UpdateProductDTO;
import org.chainoptim.features.goods.component.dto.CreateComponentDTO;
import org.chainoptim.features.goods.stage.dto.CreateStageDTO;
import org.chainoptim.features.goods.component.dto.UpdateComponentDTO;
import org.chainoptim.features.goods.stage.dto.UpdateStageDTO;
import org.chainoptim.features.storage.warehouse.dto.CreateWarehouseDTO;
import org.chainoptim.features.storage.inventory.dto.CreateWarehouseInventoryItemDTO;
import org.chainoptim.features.storage.warehouse.dto.UpdateWarehouseDTO;
import org.chainoptim.features.storage.inventory.dto.UpdateWarehouseInventoryItemDTO;
import org.chainoptim.features.supply.supplier.dto.CreateSupplierDTO;
import org.chainoptim.features.supply.supplier.dto.UpdateSupplierDTO;
import org.chainoptim.features.supply.supplierorder.dto.CreateSupplierOrderDTO;
import org.chainoptim.features.supply.supplierorder.dto.UpdateSupplierOrderDTO;
import org.chainoptim.shared.commonfeatures.location.dto.CreateLocationDTO;
import org.chainoptim.shared.commonfeatures.location.dto.UpdateLocationDTO;
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

    // Warehouses
    public CreateWarehouseDTO sanitizeCreateWarehouseDTO(CreateWarehouseDTO warehouseDTO) {
        warehouseDTO.setName(sanitizationService.sanitize(warehouseDTO.getName()));

        return warehouseDTO;
    }

    public UpdateWarehouseDTO sanitizeUpdateWarehouseDTO(UpdateWarehouseDTO warehouseDTO) {
        warehouseDTO.setName(sanitizationService.sanitize(warehouseDTO.getName()));

        return warehouseDTO;
    }

    public CreateWarehouseInventoryItemDTO sanitizeCreateWarehouseInventoryItemDTO(CreateWarehouseInventoryItemDTO itemDTO) {
        return itemDTO;
    }

    public UpdateWarehouseInventoryItemDTO sanitizeUpdateWarehouseInventoryItemDTO(UpdateWarehouseInventoryItemDTO itemDTO) {
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

    public UpdateSupplierOrderDTO sanitizeUpdateSupplierOrderDTO(UpdateSupplierOrderDTO orderDTO) {
        return orderDTO;
    }

    public CreateSupplierShipmentDTO sanitizeCreateSupplierShipmentDTO(CreateSupplierShipmentDTO shipmentDTO) {
        return shipmentDTO;
    }

    public UpdateSupplierShipmentDTO sanitizeUpdateSupplierShipmentDTO(UpdateSupplierShipmentDTO shipmentDTO) {
        return shipmentDTO;
    }

    // Clients
    public CreateClientDTO sanitizeCreateClientDTO(CreateClientDTO clientDTO) {
        clientDTO.setName(sanitizationService.sanitize(clientDTO.getName()));

        return clientDTO;
    }

    public UpdateClientDTO sanitizeUpdateClientDTO(UpdateClientDTO clientDTO) {
        clientDTO.setName(sanitizationService.sanitize(clientDTO.getName()));

        return clientDTO;
    }

    public CreateClientOrderDTO sanitizeCreateClientOrderDTO(CreateClientOrderDTO orderDTO) {
        return orderDTO;
    }

    public UpdateClientOrderDTO sanitizeUpdateClientOrderDTO(UpdateClientOrderDTO orderDTO) {
        return orderDTO;
    }

    public CreateClientShipmentDTO sanitizeCreateClientShipmentDTO(CreateClientShipmentDTO shipmentDTO) {
        return shipmentDTO;
    }

    public UpdateClientShipmentDTO sanitizeUpdateClientShipmentDTO(UpdateClientShipmentDTO shipmentDTO) {
        return shipmentDTO;
    }

    // Locations
    public CreateLocationDTO sanitizeCreateLocationDTO(CreateLocationDTO locationDTO) {
        locationDTO.setAddress(sanitizationService.sanitize(locationDTO.getAddress()));
        locationDTO.setCity(sanitizationService.sanitize(locationDTO.getCity()));
        locationDTO.setState(sanitizationService.sanitize(locationDTO.getState()));
        locationDTO.setCountry(sanitizationService.sanitize(locationDTO.getCountry()));
        locationDTO.setZipCode(sanitizationService.sanitize(locationDTO.getZipCode()));

        return locationDTO;
    }

    public UpdateLocationDTO sanitizeUpdateLocationDTO(UpdateLocationDTO locationDTO) {
        locationDTO.setAddress(sanitizationService.sanitize(locationDTO.getAddress()));
        locationDTO.setCity(sanitizationService.sanitize(locationDTO.getCity()));
        locationDTO.setState(sanitizationService.sanitize(locationDTO.getState()));
        locationDTO.setCountry(sanitizationService.sanitize(locationDTO.getCountry()));
        locationDTO.setZipCode(sanitizationService.sanitize(locationDTO.getZipCode()));

        return locationDTO;
    }

    // Component
    public CreateComponentDTO sanitizeCreateComponentDTO(CreateComponentDTO componentDTO) {
        componentDTO.setName(sanitizationService.sanitize(componentDTO.getName()));
        componentDTO.setDescription(sanitizationService.sanitize(componentDTO.getDescription()));

        return componentDTO;
    }

    public UpdateComponentDTO sanitizeUpdateComponentDTO(UpdateComponentDTO componentDTO) {
        componentDTO.setName(sanitizationService.sanitize(componentDTO.getName()));
        componentDTO.setDescription(sanitizationService.sanitize(componentDTO.getDescription()));

        return componentDTO;
    }

    // Settings
    public CreateUserSettingsDTO sanitizeCreateUserSettingsDTO(CreateUserSettingsDTO userSettingsDTO) {
        return userSettingsDTO;
    }

    public UpdateUserSettingsDTO sanitizeUpdateUserSettingsDTO(UpdateUserSettingsDTO userSettingsDTO) {
        return userSettingsDTO;
    }
}
