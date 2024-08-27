package org.chainoptim.features.goods.stage.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.goods.stage.dto.CreateStageInputDTO;
import org.chainoptim.features.goods.stage.dto.DeleteStageInputDTO;
import org.chainoptim.features.goods.stage.dto.StageDTOMapper;
import org.chainoptim.features.goods.stage.dto.UpdateStageInputDTO;
import org.chainoptim.features.goods.component.model.Component;
import org.chainoptim.features.goods.stage.model.Stage;
import org.chainoptim.features.goods.stage.model.StageInput;
import org.chainoptim.features.goods.component.repository.ComponentRepository;
import org.chainoptim.features.goods.stage.repository.StageInputRepository;
import org.chainoptim.features.goods.stage.repository.StageRepository;
import org.chainoptim.features.goods.productgraph.service.ProductProductionGraphService;
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

    public StageInput getStageInputById(Integer id) {
        return stageInputRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stage input with ID: " + id + " not found"));
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
