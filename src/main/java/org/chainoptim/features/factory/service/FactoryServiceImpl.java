package org.chainoptim.features.factory.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.factory.dto.FactoriesSearchDTO;
import org.chainoptim.features.factory.dto.FactoryDTOMapper;
import org.chainoptim.features.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.shared.search.model.PaginatedResults;

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

    public PaginatedResults<FactoriesSearchDTO> getFactoriesByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        PaginatedResults<Factory> paginatedResults = factoryRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return new PaginatedResults<>(
            paginatedResults.results.stream()
                .map(FactoryDTOMapper::convertToFactoriesSearchDTO)
                    .toList(),
            paginatedResults.totalCount
        );
    }

    public Factory createFactory(CreateFactoryDTO factoryDTO) {
        return factoryRepository.save(FactoryDTOMapper.convertCreateFactoryDTOToFactory(factoryDTO));
    }

    public Factory updateFactory(UpdateFactoryDTO factoryDTO) {
        Optional<Factory> factoryOptional = factoryRepository.findById(factoryDTO.getId());
        if (factoryOptional.isEmpty()) {
            throw new ResourceNotFoundException("The requested factory does not exist");
        }
        Factory factory = factoryOptional.get();
        factory.setName(factoryDTO.getName());

        factoryRepository.save(factory);

        return factory;
    }

    public void deleteFactory(Integer factoryId) {
        Factory factory = new Factory();
        factory.setId(factoryId);
        factoryRepository.delete(factory);
    }
}
