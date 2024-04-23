package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.shared.search.dto.SmallEntityDTO;
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

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(s.id, s.name) FROM Stage s WHERE s.productId = :productId")
    List<SmallEntityDTO> findStageNamesByProductId(@Param("productId") Integer productId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(f.id, f.name) FROM Factory f WHERE " +
            "f.id IN (SELECT fs.factory.id FROM FactoryStage fs WHERE fs.stage.productId = :productId)")
    List<SmallEntityDTO> findFactoryNamesByProductId(@Param("productId") Integer productId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(w.id, w.name) FROM Warehouse w " +
            "WHERE w.id IN (SELECT wi.warehouseId FROM WarehouseInventoryItem wi WHERE wi.product.id = :productId)")
    List<SmallEntityDTO> findWarehouseNamesByProductId(@Param("productId") Integer productId);

    @Query("SELECT new org.chainoptim.shared.search.dto.SmallEntityDTO(c.id, c.name) FROM Client c " +
            "WHERE c.id IN (SELECT co.clientId FROM ClientOrder co WHERE co.product.id = :productId)")
    List<SmallEntityDTO> findClientNamesByOrganizationId(@Param("productId") Integer productId);

    long countByOrganizationId(Integer organizationId);
}
