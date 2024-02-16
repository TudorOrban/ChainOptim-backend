package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.repository.FactoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactoryServiceImpl implements FactoryService {

    private final FactoryRepository factoryRepository;

    @Autowired
    public FactoryServiceImpl(FactoryRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }

    public List<Factory> getAllFactories() {
        return factoryRepository.findAll();
    }

    public Optional<Factory> getFactoryById(Integer id) {
        return factoryRepository.findById(id);
    }

    public List<Factory> getFactoriesByOrganizationId(Integer organizationId) {
        return factoryRepository.findByOrganizationId(organizationId);
    }
}
