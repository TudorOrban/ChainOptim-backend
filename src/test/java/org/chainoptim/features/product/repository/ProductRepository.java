package org.chainoptim.features.product.repository;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    Integer organizationId;
    Integer unitId;
    Integer productId;

    @BeforeEach
    void setUp() {
        // Set up an organization and a unit of measurement
        Organization organization = Organization.builder()
                .name("Test Org")
                .subscriptionPlan(Organization.SubscriptionPlan.PRO)
                .build();

        entityManager.persist(organization);
        entityManager.flush();
        organizationId = organization.getId();

        UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
        unitOfMeasurement.setName("Test unit");
        unitOfMeasurement.setUnitType("Test unit type");
        unitOfMeasurement.setOrganizationId(organizationId);

        entityManager.persist(unitOfMeasurement);
        entityManager.flush();
        unitId = unitOfMeasurement.getId();

        // Set up product for update and delete tests
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setOrganizationId(organizationId);
        product.setUnitId(unitId);

        entityManager.persist(product);
        entityManager.flush();
        productId = product.getId();
    }

    @Test
    void testCreateProduct() {
        // Arrange
        Product newProduct = new Product();
        newProduct.setName("Test Product 2");
        newProduct.setDescription("Test Description 2");
        newProduct.setOrganizationId(organizationId);
        newProduct.setUnitId(unitId);

        // Act
        Product savedProduct = productRepository.save(newProduct);

        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<Product> foundProductOpt = productRepository.findById(savedProduct.getId());
        assertTrue(foundProductOpt.isPresent(), "Product should be found in the database");

        Product foundProduct = foundProductOpt.get();
        assertEquals(newProduct.getName(), foundProduct.getName());
        assertEquals(newProduct.getDescription(), foundProduct.getDescription());
        assertEquals(newProduct.getOrganizationId(), foundProduct.getOrganizationId());
        assertEquals(newProduct.getUnitId(), foundProduct.getUnitId());
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        Optional<Product> productOptional = productRepository.findById(productId); // Id from setUp
        if (productOptional.isEmpty()) {
            fail("Expected an existing product with id " + productId);
        }

        Product product = productOptional.get();
        product.setName("New Test Name");
        product.setDescription("New Test Description");

        // Act
        Product updatedProduct = productRepository.save(product);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals("New Test Name", updatedProduct.getName());
        assertEquals("New Test Description", updatedProduct.getDescription());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Optional<Product> productToBeDeletedOptional = productRepository.findById(productId);
        if (productToBeDeletedOptional.isEmpty()) {
            fail("Expected an existing product with id " + productId);
        }

        Product productToBeDeleted = productToBeDeletedOptional.get();

        // Act
        productRepository.delete(productToBeDeleted);

        // Assert
        Optional<Product> deletedProductOptional = productRepository.findById(productId);
        if (deletedProductOptional.isPresent()) {
            fail("Expected product with id 1 to have been deleted");
        }
    }
}
