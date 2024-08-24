package org.chainoptim.features.factory.service;

import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.exception.ValidationException;
import org.chainoptim.features.factory.dto.*;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.chainoptim.features.factory.repository.FactoryRepository;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.shared.commonfeatures.location.model.Location;
import org.chainoptim.shared.commonfeatures.location.service.LocationService;
import org.chainoptim.shared.enums.Feature;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.dto.SmallEntityDTO;
import org.chainoptim.shared.search.model.PaginatedResults;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryServiceImpl implements FactoryService {

    private final FactoryRepository factoryRepository;
    private final LocationService locationService;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public FactoryServiceImpl(FactoryRepository factoryRepository,
                                LocationService locationService,
                                SubscriptionPlanLimiterService planLimiterService,
                                EntitySanitizerService entitySanitizerService) {
        this.factoryRepository = factoryRepository;
        this.locationService = locationService;
        this.planLimiterService = planLimiterService;
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

    public Factory getFactoryById(Integer factoryId) {
        return factoryRepository.findById(factoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Factory not found with ID: " + factoryId));
    }

    @Transactional
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

    public FactoryOverviewDTO getFactoryOverviewById(Integer factoryId) {
        List<SmallEntityDTO> factoryStages = factoryRepository.findFactoryStagesByFactoryId(factoryId);
        List<SmallEntityDTO> manufacturedComponents = factoryRepository.findManufacturedComponentsByFactoryId(factoryId);
        List<SmallEntityDTO> manufacturedProducts = factoryRepository.findManufacturedProductsByFactoryId(factoryId);
        List<SmallEntityDTO> deliveredFromSuppliers = factoryRepository.findDeliveredFromSuppliersByFactoryId(factoryId);
        List<SmallEntityDTO> deliveredToClients = factoryRepository.findDeliveredToClientsByFactoryId(factoryId);

        return new FactoryOverviewDTO(factoryStages, manufacturedComponents, manufacturedProducts, deliveredFromSuppliers, deliveredToClients);
    }

    public List<FactoryStageConnection> getFactoryStageConnectionsByFactoryId(Integer factoryId) {
        return factoryRepository.findFactoryStageConnectionsByFactoryId(factoryId);
    }

    // Create
    public Factory createFactory(CreateFactoryDTO factoryDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(factoryDTO.getOrganizationId(), Feature.FACTORY, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed factories for the current Subscription Plan.");
        }

        // Sanitize input
        CreateFactoryDTO sanitizedFactoryDTO = entitySanitizerService.sanitizeCreateFactoryDTO(factoryDTO);

        // Create location if requested
        if (sanitizedFactoryDTO.isCreateLocation() && sanitizedFactoryDTO.getLocation() != null) {
            Location location = locationService.createLocation(sanitizedFactoryDTO.getLocation());
            Factory factory = FactoryDTOMapper.convertCreateFactoryDTOToFactory(sanitizedFactoryDTO);
            factory.setLocation(location);
            return factoryRepository.save(factory);
        } else {
            return factoryRepository.save(FactoryDTOMapper.convertCreateFactoryDTOToFactory((sanitizedFactoryDTO)));
        }
    }

    // Update
    public Factory updateFactory(UpdateFactoryDTO factoryDTO) {
        UpdateFactoryDTO sanitizedFactoryDTO = entitySanitizerService.sanitizeUpdateFactoryDTO(factoryDTO);

        Factory factory = factoryRepository.findById(sanitizedFactoryDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Factory with ID: " + sanitizedFactoryDTO.getId() + " not found."));

        factory.setName(sanitizedFactoryDTO.getName());

        // Create new location or use existing or throw if not provided
        Location location;
        if (sanitizedFactoryDTO.isCreateLocation() && sanitizedFactoryDTO.getLocation() != null) {
            location = locationService.createLocation(sanitizedFactoryDTO.getLocation());
        } else if (sanitizedFactoryDTO.getLocationId() != null){
            location = new Location();
            location.setId(sanitizedFactoryDTO.getLocationId());
        } else {
            throw new ValidationException("Location is required.");
        }
        factory.setLocation(location);

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
