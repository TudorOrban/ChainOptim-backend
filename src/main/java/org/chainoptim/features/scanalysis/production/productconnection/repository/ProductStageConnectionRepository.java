package org.chainoptim.features.scanalysis.production.productconnection.repository;

import org.chainoptim.features.scanalysis.production.productconnection.model.ProductStageConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductStageConnectionRepository extends JpaRepository<ProductStageConnection, Integer> {

    @Query("SELECT c FROM ProductStageConnection c " +
            "WHERE c.productId = :productId")
    List<ProductStageConnection> findProductStageConnectionsByFactoryId(@Param("productId") Integer productId);
}
