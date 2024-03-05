package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.dto.SuppliersSearchDTO;
import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.shared.search.model.PaginatedResults;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<Supplier> getAllSuppliers();
    Optional<Supplier> getSupplierById(Integer id);
    List<Supplier> getSuppliersByOrganizationId(Integer organizationId);
    PaginatedResults<SuppliersSearchDTO> getSuppliersByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
}
