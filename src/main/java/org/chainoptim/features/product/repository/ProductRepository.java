package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, ProductsSearchRepository {

//    @Query("SELECT p FROM Product p " +
//            "LEFT JOIN FETCH p.stages s " +
//            "LEFT JOIN FETCH s.stageInputs si " +
//            "WHERE p.id = :productId")
//    Optional<Product> findByIdWithStages(@Param("productId") Integer productId);

    @EntityGraph(attributePaths = {"stages"})
    Optional<Product> findById(Integer productId);

    // Fetch all products for an organization without stages
    List<Product> findByOrganizationId(Integer organizationId);

    @Query("SELECT p.organizationId FROM Product p WHERE p.id = :productId")
    Optional<Integer> findOrganizationIdById(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p " +
            "WHERE p.name = :productName")
    Optional<Product> findByName(@Param("productName") String productName);
}
