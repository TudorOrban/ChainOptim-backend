package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.dto.CreateSupplierDTO;
import org.chainoptim.features.supply.dto.SuppliersSearchDTO;
import org.chainoptim.features.supply.dto.UpdateSupplierDTO;
import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    // Fetch
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(Integer id);
    List<Supplier> getSuppliersByOrganizationId(Integer organizationId);
    PaginatedResults<SuppliersSearchDTO> getSuppliersByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);

    // Create
    Supplier createSupplier(CreateSupplierDTO supplierDTO);

    // Update
    Supplier updateSupplier(UpdateSupplierDTO updateSupplierDTO);

    // Delete
    void deleteSupplier(Integer supplierId);
}
