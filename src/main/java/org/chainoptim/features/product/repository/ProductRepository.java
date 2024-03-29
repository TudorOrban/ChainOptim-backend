package org.chainoptim.features.product.repository;

import org.chainoptim.features.factory.dto.FactoriesSearchDTO;
import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, ProductsSearchRepository {

    List<Product> findByOrganizationId(Integer organizationId);

    @Query("SELECT new org.chainoptim.features.product.dto.ProductsSearchDTO(p.id, p.name, p.description, p.createdAt, p.updatedAt) FROM Product p WHERE p.organizationId = :organizationId")
    List<ProductsSearchDTO> findByOrganizationIdSmall(Integer organizationId);

    @Query("SELECT p.organizationId FROM Product p WHERE p.id = :productId")
    Optional<Integer> findOrganizationIdById(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p " +
            "WHERE p.name = :productName")
    Optional<Product> findByName(@Param("productName") String productName);
}
