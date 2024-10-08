package org.chainoptim.features.goods.service;

import org.chainoptim.core.tenant.subscription.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.goods.product.dto.CreateProductDTO;
import org.chainoptim.features.goods.product.dto.ProductDTOMapper;
import org.chainoptim.features.goods.product.dto.UpdateProductDTO;
import org.chainoptim.features.goods.unit.model.UnitOfMeasurement;
import org.chainoptim.features.goods.product.model.Product;
import org.chainoptim.features.goods.product.repository.ProductRepository;
import org.chainoptim.features.goods.product.service.ProductServiceImpl;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private SubscriptionPlanLimiterService planLimiterService;
    @Mock
    private EntitySanitizerService entitySanitizerService;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    void testCreateProduct() {
        // Arrange
        CreateProductDTO productDTO = new CreateProductDTO("Test product", "Test description", 1, new UnitOfMeasurement());
        Product expectedProduct = ProductDTOMapper.convertCreateProductDTOToProduct(productDTO);

        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        when(planLimiterService.isLimitReached(any(), any(), any())).thenReturn(false);
        when(entitySanitizerService.sanitizeCreateProductDTO(any(CreateProductDTO.class))).thenReturn(productDTO);

        // Act
        Product createdProduct = productService.createProduct(productDTO);

        // Assert
        assertNotNull(createdProduct);
        assertEquals(expectedProduct.getName(), createdProduct.getName());
        assertEquals(expectedProduct.getDescription(), createdProduct.getDescription());
        assertEquals(expectedProduct.getOrganizationId(), createdProduct.getOrganizationId());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_ExistingProduct() {
        // Arrange
        UpdateProductDTO productDTO = new UpdateProductDTO(1, "Test Product Update", "Test Description Update", new UnitOfMeasurement());
        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setName("Test Product");
        existingProduct.setDescription("Test Description");

        when(productRepository.findById(productDTO.getId())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        when(entitySanitizerService.sanitizeUpdateProductDTO(any(UpdateProductDTO.class))).thenReturn(productDTO);

        // Act
        Product updatedProduct = productService.updateProduct(productDTO);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals(productDTO.getName(), updatedProduct.getName());
        assertEquals(productDTO.getDescription(), updatedProduct.getDescription());

        verify(productRepository, times(1)).findById(productDTO.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_NonExistingProduct() {
        // Arrange
        UpdateProductDTO productDTO = new UpdateProductDTO(1, "Test Product Update", "Test Description Update", new UnitOfMeasurement());
        Product existingProduct = new Product();
        existingProduct.setId(2); // Different id
        existingProduct.setName("Test Product");
        existingProduct.setDescription("Test Description");

        when(productRepository.findById(productDTO.getId())).thenReturn(Optional.empty());
        when(entitySanitizerService.sanitizeUpdateProductDTO(any(UpdateProductDTO.class))).thenReturn(productDTO);

        // Act and assert
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productDTO));

        verify(productRepository, times(1)).findById(productDTO.getId());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Integer productId = 1;
        doNothing().when(productRepository).delete(any(Product.class));

        // Act
        productService.deleteProduct(productId);

        verify(productRepository, times(1)).delete(any(Product.class));
    }
}
