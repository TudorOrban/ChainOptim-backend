package org.chainoptim.features.goods.stage.repository;

import org.chainoptim.features.goods.stage.model.StageInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StageInputRepository extends JpaRepository<StageInput, Integer> {

    @Query("SELECT si FROM StageInput si WHERE si.stage.id = :stageId")
    List<StageInput> findByStageId(Integer stageId);
}
