package org.chainoptim.features.scanalysis.production.productconnection.repository;

import org.chainoptim.features.scanalysis.production.productconnection.model.ProductStageConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageConnectionRepository extends JpaRepository<ProductStageConnection, Integer> {

    @Query("SELECT c FROM ProductStageConnection c " +
            "WHERE c.productId = :productId")
    List<ProductStageConnection> findProductStageConnectionsByProductId(@Param("productId") Integer productId);

    @Query("SELECT c FROM ProductStageConnection c " +
            "WHERE c.productId = :productId " +
            "AND c.srcStageOutputId = :srcStageOutputId " +
            "AND c.destStageInputId = :destStageInputId")
    Optional<ProductStageConnection> findConnectionByStageInputAndOutputIds(@Param("productId") Integer productId,
                                                                            @Param("srcStageOutputId") Integer srcStageOutputId,
                                                                            @Param("destStageInputId") Integer destStageInputId);
}
