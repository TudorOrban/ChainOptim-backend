package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.model.Factory;

import java.util.List;
import java.util.Optional;

public interface FactoryService {
    public List<Factory> getAllFactories();
    public Optional<Factory> getFactoryById(Integer id);
    public List<Factory> getFactoriesByOrganizationId(Integer organizationId);
}
