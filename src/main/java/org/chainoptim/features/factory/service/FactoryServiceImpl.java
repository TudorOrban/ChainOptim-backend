package org.chainoptim.features.factory.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.factory.dto.CreateFactoryDTO;
import org.chainoptim.features.factory.dto.FactoriesSearchDTO;
import org.chainoptim.features.factory.dto.FactoryDTOMapper;
import org.chainoptim.features.factory.dto.UpdateFactoryDTO;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.scanalysis.production.connection.model.FactoryStageConnection;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactoryServiceImpl implements FactoryService {

    private final FactoryRepository factoryRepository;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public FactoryServiceImpl(FactoryRepository factoryRepository, EntitySanitizerService entitySanitizerService) {
        this.factoryRepository = factoryRepository;
        this.entitySanitizerService = entitySanitizerService;
    }


    // Fetch
    public List<FactoriesSearchDTO> getFactoriesByOrganizationIdSmall(Integer organizationId) {
        return factoryRepository.findByOrganizationIdSmall(organizationId);
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

    public Optional<Factory> getFactoryById(Integer factoryId) {
        return factoryRepository.findById(factoryId);
    }

    public Factory getFactoryWithStagesById(Integer factoryId) {
        Factory factory = factoryRepository.findFactoryWithStagesById(factoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Factory not found with ID: " + factoryId));

        factory.getFactoryStages().forEach(fs -> {
            Stage stage = fs.getStage();
            stage.getProductId(); // Trigger lazy loading
            Hibernate.initialize(stage.getStageInputs());
            Hibernate.initialize(stage.getStageOutputs());
        });
        return factory;
    }

    public List<FactoryStageConnection> getFactoryStageConnectionsByFactoryId(Integer factoryId) {
        return factoryRepository.findFactoryStageConnectionsByFactoryId(factoryId);
    }

    // Create
    public Factory createFactory(CreateFactoryDTO factoryDTO) {
        CreateFactoryDTO sanitizedFactoryDTO = entitySanitizerService.sanitizeCreateFactoryDTO(factoryDTO);
        return factoryRepository.save(FactoryDTOMapper.convertCreateFactoryDTOToFactory(sanitizedFactoryDTO));
    }

    // Update
    public Factory updateFactory(UpdateFactoryDTO factoryDTO) {
        UpdateFactoryDTO sanitizedFactoryDTO = entitySanitizerService.sanitizeUpdateFactoryDTO(factoryDTO);

        Factory factory = factoryRepository.findById(sanitizedFactoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Factory with ID: " + sanitizedFactoryDTO.getId() + " not found."));

        factory.setName(sanitizedFactoryDTO.getName());

        factoryRepository.save(factory);
        return factory;
    }

    // Delete
    public void deleteFactory(Integer factoryId) {
        Factory factory = new Factory();
        factory.setId(factoryId);
        factoryRepository.delete(factory);
    }
}
