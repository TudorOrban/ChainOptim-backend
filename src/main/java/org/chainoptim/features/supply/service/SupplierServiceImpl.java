package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.dto.SuppliersSearchDTO;
import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.features.supply.repository.SupplierRepository;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Integer id) {
        return supplierRepository.findById(id);
    }

    public List<Supplier> getSuppliersByOrganizationId(Integer organizationId) {
        return supplierRepository.findByOrganizationId(organizationId);
    }

    public PaginatedResults<SuppliersSearchDTO> getSuppliersByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        PaginatedResults<Supplier> paginatedResults = supplierRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return new PaginatedResults<>(
            paginatedResults.results.stream()
            .map(this::convertToSuppliersSearchDTO)
            .toList(),
            paginatedResults.totalCount
        );
    }

    public SuppliersSearchDTO convertToSuppliersSearchDTO(Supplier suppliers) {
        SuppliersSearchDTO dto = new SuppliersSearchDTO();
        dto.setId(suppliers.getId());
        dto.setName(suppliers.getName());
        dto.setCreatedAt(suppliers.getCreatedAt());
        dto.setUpdatedAt(suppliers.getUpdatedAt());
        dto.setLocation(suppliers.getLocation());
        return dto;
    }
}
