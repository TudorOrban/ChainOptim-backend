package org.chainoptim.features.supply.dto;

import org.chainoptim.features.supply.model.Supplier;

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
}
