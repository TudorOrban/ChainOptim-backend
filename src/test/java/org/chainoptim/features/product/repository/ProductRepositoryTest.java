package org.chainoptim.features.product.repository;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.features.client.model.Client;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.factory.model.FactoryStage;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.features.productpipeline.model.Stage;
import org.chainoptim.features.warehouse.model.Warehouse;
import org.chainoptim.features.warehouse.model.WarehouseInventoryItem;
import org.chainoptim.shared.search.dto.SmallEntityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ExtendWith(SpringExtension.class)
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
                .subscriptionPlanTier(Organization.SubscriptionPlanTier.PRO)
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
        Product product = addTestProduct();
        productId = product.getId();
    }

    @Test
    void testFindStageNamesByProductId() {
        // Arrange
        Product savedProduct = addTestProduct();

        Stage savedStage = addTestStage(savedProduct);

        // Act
        List<SmallEntityDTO> stageNames = productRepository.findStageNamesByProductId(savedProduct.getId());

        // Assert
        assertNotNull(stageNames);
        assertEquals(1, stageNames.size());
        assertEquals("Test Stage", stageNames.getFirst().getName());
        assertEquals(savedStage.getId(), stageNames.getFirst().getId());
    }

    @Test
    void testFindFactoryNamesByProductId() {
        // Arrange
        // - Product and stage
        Product savedProduct = addTestProduct();

        Stage savedStage = addTestStage(savedProduct);

        // - Factory and FactoryStage
        Factory factory = new Factory();
        factory.setName("Test Factory");
        factory.setOrganizationId(organizationId);

        entityManager.persist(factory);
        entityManager.flush();

        FactoryStage factoryStage = new FactoryStage();
        factoryStage.setFactory(factory);
        factoryStage.setStage(savedStage);

        entityManager.persist(factoryStage);
        entityManager.flush();

        // Act
        List<SmallEntityDTO> factoryNames = productRepository.findFactoryNamesByProductId(savedProduct.getId());

        // Assert
        assertNotNull(factoryNames);
        assertEquals(1, factoryNames.size());
        assertEquals("Test Factory", factoryNames.getFirst().getName());
        assertEquals(factory.getId(), factoryNames.getFirst().getId());
    }

    @Test
    void findWarehouseNamesByProductId() {
        // Arrange
        // - Product
        Product savedProduct = addTestProduct();

        // - Warehouse and WarehouseInventoryItem
        Warehouse warehouse = new Warehouse();
        warehouse.setName("Test Warehouse");
        warehouse.setOrganizationId(organizationId);

        entityManager.persist(warehouse);
        entityManager.flush();

        WarehouseInventoryItem warehouseInventoryItem = new WarehouseInventoryItem();
        warehouseInventoryItem.setWarehouseId(warehouse.getId());
        warehouseInventoryItem.setProduct(savedProduct);

        entityManager.persist(warehouseInventoryItem);
        entityManager.flush();

        // Act
        List<SmallEntityDTO> warehouseNames = productRepository.findWarehouseNamesByProductId(savedProduct.getId());

        // Assert
        assertNotNull(warehouseNames);
        assertEquals(1, warehouseNames.size());
        assertEquals("Test Warehouse", warehouseNames.getFirst().getName());
        assertEquals(warehouse.getId(), warehouseNames.getFirst().getId());
    }

    @Test
    void testFindClientNamesByOrganizationId() {
        // Arrange
        // - Product
        Product savedProduct = addTestProduct();

        // - Client and ClientOrder
        Client client = new Client();
        client.setName("Test Client");
        client.setOrganizationId(organizationId);

        entityManager.persist(client);
        entityManager.flush();

        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClientId(client.getId());
        clientOrder.setProduct(savedProduct);
        clientOrder.setOrganizationId(organizationId);

        entityManager.persist(clientOrder);
        entityManager.flush();

        // Act
        List<SmallEntityDTO> clientNames = productRepository.findClientNamesByOrganizationId(savedProduct.getId());

        // Assert
        assertNotNull(clientNames);
        assertEquals(1, clientNames.size());
        assertEquals("Test Client", clientNames.getFirst().getName());
        assertEquals(client.getId(), clientNames.getFirst().getId());
    }

    @Test
    void testCreateProduct() {
        // Arrange
        Product savedProduct = addTestProduct();

        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<Product> foundProductOpt = productRepository.findById(savedProduct.getId());
        assertTrue(foundProductOpt.isPresent(), "Product should be found in the database");

        Product foundProduct = foundProductOpt.get();
        assertEquals(savedProduct.getName(), foundProduct.getName());
        assertEquals(savedProduct.getDescription(), foundProduct.getDescription());
        assertEquals(savedProduct.getOrganizationId(), foundProduct.getOrganizationId());
        assertEquals(savedProduct.getUnit().getId(), foundProduct.getUnit().getId());
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

    Product addTestProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setOrganizationId(organizationId);
        UnitOfMeasurement unit = new UnitOfMeasurement();
        unit.setId(unitId);
        product.setUnit(unit);

        return entityManager.persist(product);
    }

    Stage addTestStage(Product product) {
        Stage stage = new Stage();
        stage.setName("Test Stage");
        stage.setOrganizationId(organizationId);
        stage.setProduct(product);
        stage.setProductId(product.getId());

        return entityManager.persist(stage);
    }

}
