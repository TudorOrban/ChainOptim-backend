package org.chainoptim.features.supplier.dto;

import org.chainoptim.features.supplier.model.Supplier;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.shared.commonfeatures.location.model.Location;

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
        if (supplierDTO.getLocationId() != null) {
            Location location = new Location();
            location.setId(supplierDTO.getLocationId());
            supplier.setLocation(location);
        }

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
