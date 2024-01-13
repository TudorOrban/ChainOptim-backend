package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Fetch a product with its pipeline
//    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.stages WHERE p.id = :productId")
//    Optional<Product> findByIdWithStages(@Param("productId") Integer productId);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.stages s LEFT JOIN FETCH s.stageInputs LEFT JOIN FETCH s.stageOutputs WHERE p.id = :productId")
    Optional<Product> findByIdWithStages(@Param("productId") Integer productId);

    // Fetch all products for an organization without stages
    List<Product> findByOrganizationId(Integer organizationId);
}
