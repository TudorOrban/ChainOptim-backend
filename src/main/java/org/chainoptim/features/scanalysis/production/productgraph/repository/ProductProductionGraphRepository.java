package org.chainoptim.features.scanalysis.production.productgraph.repository;

import org.chainoptim.features.scanalysis.production.productgraph.model.ProductProductionGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductProductionGraphRepository extends JpaRepository<ProductProductionGraph, Integer> {

    @Query("SELECT pg FROM ProductProductionGraph pg " +
            "WHERE pg.productId = :productId")
    List<ProductProductionGraph> findProductionGraphByProductId(@Param("productId") Integer productId);
}
