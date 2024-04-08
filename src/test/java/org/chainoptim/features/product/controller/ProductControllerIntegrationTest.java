package org.chainoptim.features.product.controller;

import org.chainoptim.features.product.dto.*;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.features.product.repository.UnitOfMeasurementRepository;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.chainoptim.testutil.TestDataSeederService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Seed data services
    @Autowired
    private TestDataSeederService seederService;
    @Autowired
    private UnitOfMeasurementRepository unitOfMeasurementRepository;
    @Autowired
    private ProductRepository productRepository;

    // Necessary seed data
    Integer organizationId;
    String jwtToken;
    CreateUnitOfMeasurementDTO unitDTO;
    UnitOfMeasurement unit;
    Integer productId;

    @BeforeEach
    void setUp() {
        // Set up an organization, user, and JWT token for passing security checks
        Pair<Integer, String> seedResult = seederService.seedDatabaseWithTenant();
        organizationId = seedResult.getFirst();
        jwtToken = seedResult.getSecond();

        // Set up a unit of measurement for products
        unitDTO = new CreateUnitOfMeasurementDTO();
        unitDTO.setName("Test unit");
        unitDTO.setUnitType("Test unit type");
        unitDTO.setOrganizationId(organizationId);

        unit = unitOfMeasurementRepository.save(UnitDTOMapper.convertCreateUnitDTOToUnit(unitDTO));

        // Set up product for search, update and delete tests
        createTestProducts();
    }

    void createTestProducts() {
        Product product1 = new Product();
        product1.setName("Test Product 1");
        product1.setDescription("Test Description 1");
        product1.setOrganizationId(organizationId);
        product1.setUnit(unit);

        product1 = productRepository.save(product1);
        productId = product1.getId();

        Product product2 = new Product();
        product2.setName("Test Product 2");
        product2.setDescription("Test Description 2");
        product2.setOrganizationId(organizationId);
        product2.setUnit(unit);

        productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Test Product 3");
        product3.setDescription("Test Description 3");
        product3.setOrganizationId(organizationId);
        product3.setUnit(unit);

        productRepository.save(product3);
    }

    @Test
    void testSearchProducts() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/products/organization/advanced/" + organizationId.toString()
                + "?searchQuery=Test"
                + "&sortOption=name"
                + "&ascending=true"
                + "&page=1"
                + "&itemsPerPage=2";
        String invalidJWTToken = "Invalid";

        // Act and assert error status for invalid credentials
        MvcResult invalidMvcResult = mockMvc.perform(get(url)
                .header("Authorization", "Bearer " + invalidJWTToken))
                .andExpect(status().is(500))
                .andReturn();

        // Act
        MvcResult mvcResult = mockMvc.perform(get(url)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        // Extract and deserialize response
        String responseContent = mvcResult.getResponse().getContentAsString();
        PaginatedResults<ProductsSearchDTO> paginatedResults = objectMapper.readValue(
                responseContent, new TypeReference<PaginatedResults<ProductsSearchDTO>>() {});

        // Assert
        assertNotNull(paginatedResults);
        assertEquals(2, paginatedResults.results.size()); // Ensure pagination works
        assertEquals(3, paginatedResults.totalCount); // Ensure total count works
        assertEquals(productId, paginatedResults.results.getFirst().getId()); // Ensure sorting works
    }

    @Test
    void testCreateProduct() throws Exception {
        // Arrange
        CreateProductDTO productDTO = new CreateProductDTO("Test Product - Unique Title 123456789", "Test Description", organizationId, unit.getId(), unitDTO, false);
        String productDTOJson = objectMapper.writeValueAsString(productDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(post("/api/v1/products/create")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDTOJson))
                        .andExpect(status().is(500));

        // Assert
        Optional<Product> invalidCreatedProductOptional = productRepository.findByName(productDTO.getName());
        if (invalidCreatedProductOptional.isPresent()) {
            fail("Failed to prevent creation on invalid JWT token");
        }

        // Act
        mockMvc.perform(post("/api/v1/products/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDTOJson));

        // Assert
        Optional<Product> createdProductOptional = productRepository.findByName(productDTO.getName());
        if (createdProductOptional.isEmpty()) {
            fail("Created product has not been found");
        }
        Product createdProduct = createdProductOptional.get();
        assertNotNull(createdProduct);
        assertEquals(productDTO.getName(), createdProduct.getName());
        assertEquals(productDTO.getDescription(), createdProduct.getDescription());
        assertEquals(productDTO.getOrganizationId(), createdProduct.getOrganizationId());
        assertEquals(productDTO.getUnitId(), createdProduct.getUnit().getId());

    }

    @Test
    void testUpdateProduct() throws Exception {
        // Arrange
        UpdateProductDTO productDTO = new UpdateProductDTO(productId, "Test Product - Updated Unique Title 123456789", "Test Description", unit.getId());
        String productDTOJson = objectMapper.writeValueAsString(productDTO);
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(put("/api/v1/products/update")
                        .header("Authorization", "Bearer " + invalidJWTToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDTOJson))
                        .andExpect(status().is(500));

        // Assert
        Optional<Product> invalidUpdatedProductOptional = productRepository.findByName(productDTO.getName());
        if (invalidUpdatedProductOptional.isPresent()) {
            fail("Failed to prevent update on invalid JWT token.");
        }

        // Act
        mockMvc.perform(put("/api/v1/products/update")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDTOJson))
                        .andExpect(status().isOk());

        // Assert
        Optional<Product> updatedProductOptional = productRepository.findByName(productDTO.getName());
        if (updatedProductOptional.isEmpty()) {
            fail("Updated product has not been found");
        }
        Product updatedProduct = updatedProductOptional.get();
        assertNotNull(updatedProduct);
        assertEquals(productDTO.getName(), updatedProduct.getName());
        assertEquals(productDTO.getDescription(), updatedProduct.getDescription());
        assertEquals(productDTO.getUnitId(), updatedProduct.getUnit().getId());
    }

    @Test
    void testDeleteProduct() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/v1/products/delete/" + productId;
        String invalidJWTToken = "Invalid";

        // Act (invalid security credentials)
        mockMvc.perform(delete(url)
                        .header("Authorization", "Bearer " + invalidJWTToken))
                        .andExpect(status().is(500));

        // Assert
        Optional<Product> invalidUpdatedProductOptional = productRepository.findById(productId);
        if (invalidUpdatedProductOptional.isEmpty()) {
            fail("Failed to prevent deletion on invalid JWT Token.");
        }

        // Act
        mockMvc.perform(delete(url)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        // Assert
        Optional<Product> updatedProductOptional = productRepository.findById(productId);
        if (updatedProductOptional.isPresent()) {
            fail("Product has not been deleted as expected.");
        }
    }

}
