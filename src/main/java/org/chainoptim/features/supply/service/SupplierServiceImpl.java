package org.chainoptim.features.supply.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.supply.dto.CreateSupplierDTO;
import org.chainoptim.features.supply.dto.SupplierDTOMapper;
import org.chainoptim.features.supply.dto.SuppliersSearchDTO;
import org.chainoptim.features.supply.dto.UpdateSupplierDTO;
import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.features.supply.repository.SupplierRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, EntitySanitizerService entitySanitizerService) {
        this.supplierRepository = supplierRepository;
        this.entitySanitizerService = entitySanitizerService;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Integer supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID: " + supplierId + " not found."));
    }

    public List<Supplier> getSuppliersByOrganizationId(Integer organizationId) {
        return supplierRepository.findByOrganizationId(organizationId);
    }

    public PaginatedResults<SuppliersSearchDTO> getSuppliersByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        PaginatedResults<Supplier> paginatedResults = supplierRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return new PaginatedResults<>(
            paginatedResults.results.stream()
            .map(SupplierDTOMapper::convertToSuppliersSearchDTO)
            .toList(),
            paginatedResults.totalCount
        );
    }

    public Supplier createSupplier(CreateSupplierDTO supplierDTO) {
        CreateSupplierDTO sanitizedSupplierDTO = entitySanitizerService.sanitizeCreateSupplierDTO(supplierDTO);
        return supplierRepository.save(SupplierDTOMapper.convertCreateSupplierDTOToSupplier(sanitizedSupplierDTO));
    }

    public Supplier updateSupplier(UpdateSupplierDTO supplierDTO) {
        UpdateSupplierDTO sanitizedSupplierDTO = entitySanitizerService.sanitizeUpdateSupplierDTO(supplierDTO);
        Supplier supplier = supplierRepository.findById(sanitizedSupplierDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with ID: " + sanitizedSupplierDTO.getId() + " not found."));

        supplier.setName(sanitizedSupplierDTO.getName());

        supplierRepository.save(supplier);
        return supplier;
    }

    public void deleteSupplier(Integer supplierId) {
        Supplier supplier = new Supplier();
        supplier.setId(supplierId);
        supplierRepository.delete(supplier);
    }
}
