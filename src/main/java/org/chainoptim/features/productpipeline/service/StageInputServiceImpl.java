package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.dto.CreateStageInputDTO;
import org.chainoptim.features.productpipeline.dto.DeleteStageInputDTO;
import org.chainoptim.features.productpipeline.dto.StageDTOMapper;
import org.chainoptim.features.productpipeline.dto.UpdateStageInputDTO;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.model.StageInput;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.chainoptim.features.productpipeline.repository.StageInputRepository;
import org.chainoptim.features.productpipeline.repository.StageRepository;
import org.chainoptim.features.scanalysis.production.productgraph.model.ProductProductionGraph;
import org.chainoptim.features.scanalysis.production.productgraph.service.ProductProductionGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StageInputServiceImpl implements StageInputService {

    private final StageInputRepository stageInputRepository;
    private final StageRepository stageRepository;
    private final ComponentRepository componentRepository;
    private final ProductProductionGraphService graphService;

    @Autowired
    public StageInputServiceImpl(
            StageInputRepository stageInputRepository,
            StageRepository stageRepository,
            ComponentRepository componentRepository,
            ProductProductionGraphService graphService) {
        this.stageInputRepository = stageInputRepository;
        this.stageRepository = stageRepository;
        this.componentRepository = componentRepository;
        this.graphService = graphService;
    }

    public List<StageInput> getStageInputsByStageId(Integer stageId) {
        return stageInputRepository.findByStageId(stageId);
    }

    public StageInput createStageInput(CreateStageInputDTO inputDTO) {
        StageInput stageInput = StageDTOMapper.mapCreateStageInputDTOToStageInput(inputDTO);

        Optional<Stage> stage = stageRepository.findById(inputDTO.getStageId());
        if (stage.isEmpty()) {
            throw new IllegalArgumentException("Stage not found");
        }
        stageInput.setStage(stage.get());

        Optional<Component> component = componentRepository.findById(inputDTO.getComponentId());
        if (component.isEmpty()) {
            throw new IllegalArgumentException("Component not found");
        }
        stageInput.setComponent(component.get());

        StageInput savedInput = stageInputRepository.save(stageInput);

        graphService.updateProductGraph(inputDTO.getProductId());

        return savedInput;
    }

    public StageInput updateStageInput(UpdateStageInputDTO inputDTO) {
        Optional<StageInput> stageInputOptional = stageInputRepository.findById(inputDTO.getId());
        if (stageInputOptional.isEmpty()) {
            throw new IllegalArgumentException("Stage input not found");
        }

        StageInput stageInput = stageInputOptional.get();
        StageInput updatedInput = StageDTOMapper.setUpdateStageInputDTOToStageInput(inputDTO, stageInput);

        StageInput savedInput = stageInputRepository.save(updatedInput);

        graphService.updateProductGraph(inputDTO.getProductId());

        return savedInput;
    }

    public void deleteStageInput(DeleteStageInputDTO inputDTO) {
        Optional<StageInput> stageInputOptional = stageInputRepository.findById(inputDTO.getStageInputId());
        if (stageInputOptional.isEmpty()) {
            throw new IllegalArgumentException("Stage input not found");
        }

        stageInputRepository.delete(stageInputOptional.get());

        graphService.updateProductGraph(inputDTO.getProductId());
    }
}
