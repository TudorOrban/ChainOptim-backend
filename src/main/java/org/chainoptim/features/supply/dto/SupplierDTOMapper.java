package org.chainoptim.features.supply.dto;

import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.features.supply.model.SupplierOrder;

public class SupplierDTOMapper {

    private SupplierDTOMapper() {}

    public static SuppliersSearchDTO convertToSuppliersSearchDTO(Supplier supplier) {
        SuppliersSearchDTO dto = new SuppliersSearchDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setCreatedAt(supplier.getCreatedAt());
        dto.setUpdatedAt(supplier.getUpdatedAt());
        dto.setLocation(supplier.getLocation());
        return dto;
    }

    public static Supplier convertCreateSupplierDTOToSupplier(CreateSupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setOrganizationId(supplierDTO.getOrganizationId());

        return supplier;
    }

    public static SupplierOrder mapCreateDtoToSupplierOrder(CreateSupplierOrderDTO order) {
        SupplierOrder supplierOrder = new SupplierOrder();
        supplierOrder.setOrganizationId(order.getOrganizationId());
        supplierOrder.setSupplierId(order.getSupplierId());
        supplierOrder.setComponentId(order.getComponentId());
        supplierOrder.setQuantity(order.getQuantity());
        supplierOrder.setOrderDate(order.getOrderDate());
        supplierOrder.setEstimatedDeliveryDate(order.getEstimatedDeliveryDate());
        supplierOrder.setDeliveryDate(order.getDeliveryDate());
        supplierOrder.setStatus(order.getStatus());

        return supplierOrder;
    }
}
