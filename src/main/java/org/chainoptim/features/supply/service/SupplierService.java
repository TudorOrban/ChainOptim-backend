package org.chainoptim.features.supply.service;

import org.chainoptim.features.supply.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    public List<Supplier> getAllSuppliers();
    public Optional<Supplier> getSupplierById(Integer id);
    public List<Supplier> getSuppliersByOrganizationId(Integer organizationId);
}
