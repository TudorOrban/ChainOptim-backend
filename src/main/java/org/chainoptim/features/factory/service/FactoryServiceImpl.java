package org.chainoptim.features.factory.service;

import org.chainoptim.features.factory.dto.FactoriesSearchDTO;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.repository.FactoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<FactoriesSearchDTO> getFactoriesByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending) {
        List<Factory> factories = factoryRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending);
        return factories.stream()
                .map(this::convertToFactoriesSearchDTO)
                .collect(Collectors.toList());
    }

    public FactoriesSearchDTO convertToFactoriesSearchDTO(Factory factory) {
        FactoriesSearchDTO dto = new FactoriesSearchDTO();
        dto.setId(factory.getId());
        dto.setName(factory.getName());
        dto.setCreatedAt(factory.getCreatedAt());
        dto.setUpdatedAt(factory.getUpdatedAt());
        dto.setLocation(factory.getLocation());
        return dto;
    }
}
