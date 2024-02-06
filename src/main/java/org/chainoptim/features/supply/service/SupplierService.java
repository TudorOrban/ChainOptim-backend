package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.model.Supplier;
import org.chainoptim.features.supply.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Integer id) {
        return supplierRepository.findById(id);
    }

    public List<Supplier> getSuppliersByOrganizationId(Integer organizationId) {
        return supplierRepository.findByOrganizationId(organizationId);
    }
}
