package org.chainoptim.features.storage.service;

import org.chainoptim.core.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.features.storage.dto.CrateDTOMapper;
import org.chainoptim.features.storage.dto.CreateCrateDTO;
import org.chainoptim.features.storage.dto.UpdateCrateDTO;
import org.chainoptim.features.storage.model.Crate;
import org.chainoptim.features.storage.repository.CrateRepository;
import org.chainoptim.shared.enums.Feature;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrateServiceImpl implements CrateService {

    private final CrateRepository crateRepository;
    private final SubscriptionPlanLimiterService planLimiterService;

    @Autowired
    public CrateServiceImpl(CrateRepository crateRepository,
                            SubscriptionPlanLimiterService planLimiterService) {
        this.crateRepository = crateRepository;
        this.planLimiterService = planLimiterService;
    }

    public List<Crate> getCratesByOrganizationId(Integer organizationId) {
        return crateRepository.findByOrganizationId(organizationId);
    }

    public Crate createCrate(CreateCrateDTO crateDTO) {
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(crateDTO.getOrganizationId(), Feature.CRATE, 1)) {
            throw new PlanLimitReachedException("You have reached the limit of allowed crates for the current Subscription Plan.");
        }

        Crate crate = CrateDTOMapper.mapCreateCrateDTOToCrate(crateDTO);
        return crateRepository.save(crate);
    }

    public Crate updateCrate(UpdateCrateDTO crateDTO) {
        Crate crate = crateRepository.findById(crateDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Crate with ID: " + crateDTO.getId() + " not found."));
        CrateDTOMapper.setUpdateCrateDTOToCrate(crateDTO, crate);
        return crateRepository.save(crate);
    }

    public void deleteCrate(Integer crateId) {
        crateRepository.deleteById(crateId);
    }
}
