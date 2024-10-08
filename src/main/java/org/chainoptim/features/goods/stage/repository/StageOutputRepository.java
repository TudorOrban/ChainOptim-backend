package org.chainoptim.features.goods.stage.repository;

import org.chainoptim.features.goods.stage.model.StageOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StageOutputRepository extends JpaRepository<StageOutput, Integer> {

    @Query("SELECT so FROM StageOutput so WHERE so.stage.id = :stageId")
    List<StageOutput> findByStageId(Integer stageId);
}
