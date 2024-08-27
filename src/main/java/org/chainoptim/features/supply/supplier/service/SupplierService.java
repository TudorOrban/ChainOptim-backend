package org.chainoptim.features.supply.supplier.service;

import org.chainoptim.features.supply.supplier.dto.CreateSupplierDTO;
import org.chainoptim.features.supply.supplier.dto.SupplierOverviewDTO;
import org.chainoptim.features.supply.supplier.dto.SuppliersSearchDTO;
import org.chainoptim.features.supply.supplier.dto.UpdateSupplierDTO;
import org.chainoptim.features.supply.supplier.model.Supplier;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;

public interface SupplierService {
    // Fetch
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(Integer id);
    List<Supplier> getSuppliersByOrganizationId(Integer organizationId);
    PaginatedResults<SuppliersSearchDTO> getSuppliersByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
    SupplierOverviewDTO getSupplierOverview(Integer supplierId);

    // Create
    Supplier createSupplier(CreateSupplierDTO supplierDTO);

    // Update
    Supplier updateSupplier(UpdateSupplierDTO updateSupplierDTO);

    // Delete
    void deleteSupplier(Integer supplierId);
}
