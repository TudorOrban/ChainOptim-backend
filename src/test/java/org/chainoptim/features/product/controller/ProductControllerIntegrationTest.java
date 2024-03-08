package org.chainoptim.features.product.controller;

import org.chainoptim.core.organization.model.Organization;
import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.features.product.repository.UnitOfMeasurementRepository;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UnitOfMeasurementRepository unitOfMeasurementRepository;
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

        organization = organizationRepository.save(organization);
        organizationId = organization.getId();

        UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement();
        unitOfMeasurement.setName("Test unit");
        unitOfMeasurement.setUnitType("Test unit type");
        unitOfMeasurement.setOrganizationId(organizationId);

        unitOfMeasurement = unitOfMeasurementRepository.save(unitOfMeasurement);
        unitId = unitOfMeasurement.getId();

        // Set up product for search, update and delete tests
        createTestProducts();
    }

    void createTestProducts() {
        Product product1 = new Product();
        product1.setName("Test Product 1");
        product1.setDescription("Test Description 1");
        product1.setOrganizationId(organizationId);
        product1.setUnitId(unitId);

        product1 = productRepository.save(product1);
        productId = product1.getId();

        Product product2 = new Product();
        product2.setName("Test Product 2");
        product2.setDescription("Test Description 2");
        product2.setOrganizationId(organizationId);
        product2.setUnitId(unitId);

        productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Test Product 3");
        product3.setDescription("Test Description 3");
        product3.setOrganizationId(organizationId);
        product3.setUnitId(unitId);

        productRepository.save(product3);
    }

    @Test
    void testSearchProducts() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/products/organizations/advanced/" + organizationId.toString()
                + "?searchQuery=Test"
                + "&sortOption=name"
                + "&ascending=true"
                + "&page=1"
                + "&itemsPerPage=2";

        // Act
        MvcResult mvcResult = mockMvc.perform(get(url))
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
        CreateProductDTO productDTO = new CreateProductDTO("Test Product - Unique Title 123456789", "Test Description", organizationId, unitId);
        String productDTOJson = objectMapper.writeValueAsString(productDTO);

        // Act
        mockMvc.perform(post("/api/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productDTOJson))
                .andExpect(status().isOk());

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
        assertEquals(productDTO.getUnitId(), createdProduct.getUnitId());

    }

    @Test
    void testUpdateProduct() throws Exception {
        // Arrange
        UpdateProductDTO productDTO = new UpdateProductDTO(productId, "Test Product - Updated Unique Title 123456789", "Test Description", unitId);
        String productDTOJson = objectMapper.writeValueAsString(productDTO);

        // Act
        mockMvc.perform(put("/api/products/update")
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
        assertEquals(productDTO.getUnitId(), updatedProduct.getUnitId());
    }

    @Test
    void testDeleteProduct() throws Exception {
        // Arrange
        String url = "http://localhost:8080/api/products/delete/" + productId;

        // Act
        mockMvc.perform(delete(url))
                .andExpect(status().isOk());

        // Assert
        Optional<Product> updatedProductOptional = productRepository.findById(productId);
        if (updatedProductOptional.isPresent()) {
            fail("Deleted product has been found");
        }
    }

}
