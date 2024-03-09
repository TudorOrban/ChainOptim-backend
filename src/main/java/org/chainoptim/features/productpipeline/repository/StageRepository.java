package org.chainoptim.features.productpipeline.repository;

import org.chainoptim.features.productpipeline.model.Stage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StageRepository extends JpaRepository<Stage, Integer> {

    @EntityGraph(attributePaths = {"stageInputs", "stageInputs.components", "stageOutputs", "stageOutputs.components"})
    Optional<Stage> findById(Integer id);
}
