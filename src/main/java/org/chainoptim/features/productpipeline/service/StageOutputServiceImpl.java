package org.chainoptim.features.productpipeline.service;

import org.chainoptim.features.productpipeline.dto.CreateStageOutputDTO;
import org.chainoptim.features.productpipeline.dto.DeleteStageOutputDTO;
import org.chainoptim.features.productpipeline.dto.StageDTOMapper;
import org.chainoptim.features.productpipeline.dto.UpdateStageOutputDTO;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.productpipeline.model.StageOutput;
import org.chainoptim.features.productpipeline.repository.ComponentRepository;
import org.chainoptim.features.productpipeline.repository.StageOutputRepository;
import org.chainoptim.features.productpipeline.repository.StageRepository;
import org.chainoptim.features.scanalysis.production.productgraph.service.ProductProductionGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StageOutputServiceImpl implements StageOutputService {

    private final StageOutputRepository stageOutputRepository;
    private final StageRepository stageRepository;
    private final ComponentRepository componentRepository;
    private final ProductProductionGraphService graphService;

    @Autowired
    public StageOutputServiceImpl(
            StageOutputRepository stageOutputRepository,
            StageRepository stageRepository,
            ComponentRepository componentRepository,
            ProductProductionGraphService graphService) {
        this.stageOutputRepository = stageOutputRepository;
        this.stageRepository = stageRepository;
        this.componentRepository = componentRepository;
        this.graphService = graphService;
    }

    public List<StageOutput> getStageOutputsByStageId(Integer stageId) {
        return stageOutputRepository.findByStageId(stageId);
    }

    public StageOutput createStageOutput(CreateStageOutputDTO outputDTO) {
        StageOutput stageOutput = StageDTOMapper.mapCreateStageOutputDTOToStageOutput(outputDTO);

        Optional<Stage> stage = stageRepository.findById(outputDTO.getStageId());
        if (stage.isEmpty()) {
            throw new IllegalArgumentException("Stage not found");
        }
        stageOutput.setStage(stage.get());

        Optional<Component> component = componentRepository.findById(outputDTO.getComponentId());
        if (component.isEmpty()) {
            throw new IllegalArgumentException("Component not found");
        }
        stageOutput.setComponent(component.get());

        StageOutput savedOutput = stageOutputRepository.save(stageOutput);

        graphService.updateProductGraph(outputDTO.getProductId());

        return savedOutput;
    }

    public StageOutput updateStageOutput(UpdateStageOutputDTO outputDTO) {
        Optional<StageOutput> stageOutputOptional = stageOutputRepository.findById(outputDTO.getId());
        if (stageOutputOptional.isEmpty()) {
            throw new IllegalArgumentException("Stage output not found");
        }

        StageOutput stageOutput = stageOutputOptional.get();
        StageDTOMapper.setUpdateStageOutputDTOToStageOutput(outputDTO, stageOutput);

        StageOutput savedOutput = stageOutputRepository.save(stageOutput);

        graphService.updateProductGraph(outputDTO.getProductId());

        return savedOutput;
    }

    public void deleteStageOutput(DeleteStageOutputDTO outputDTO) {
        Optional<StageOutput> stageOutputOptional = stageOutputRepository.findById(outputDTO.getStageOutputId());
        if (stageOutputOptional.isEmpty()) {
            throw new IllegalArgumentException("Stage output not found");
        }

        stageOutputRepository.delete(stageOutputOptional.get());

        graphService.updateProductGraph(outputDTO.getProductId());
    }
}
