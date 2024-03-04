package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.dto.SuppliersSearchDTO;
import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.features.supply.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<SuppliersSearchDTO> getSuppliersByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending) {
        List<Supplier> suppliers = supplierRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending);
        return suppliers.stream()
                .map(this::convertToSuppliersSearchDTO)
                .collect(Collectors.toList());
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
