package org.chainoptim.features.product.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.ProductDTOMapper;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.repository.ProductRepository;
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

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testCreateProduct() {
        // Arrange
        CreateProductDTO productDTO = new CreateProductDTO("Test product", "Test description", 1, 1);
        Product expectedProduct = ProductDTOMapper.convertCreateProductDTOToProduct(productDTO);

        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);

        // Act
        Product createdProduct = productService.createProduct(productDTO);

        // Assert
        assertNotNull(createdProduct);
        assertEquals(expectedProduct.getName(), createdProduct.getName());
        assertEquals(expectedProduct.getDescription(), createdProduct.getDescription());
        assertEquals(expectedProduct.getOrganizationId(), createdProduct.getOrganizationId());
        assertEquals(expectedProduct.getUnitId(), createdProduct.getUnitId());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_ExistingProduct() {
        // Arrange
        UpdateProductDTO productDTO = new UpdateProductDTO(1, "Test Product Update", "Test Description Update", 2);
        Product existingProduct = new Product();
        existingProduct.setId(1);
        existingProduct.setName("Test Product");
        existingProduct.setDescription("Test Description");
        existingProduct.setUnitId(1);

        when(productRepository.findById(productDTO.getId())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Act
        Product updatedProduct = productService.updateProduct(productDTO);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals(productDTO.getName(), updatedProduct.getName());
        assertEquals(productDTO.getDescription(), updatedProduct.getDescription());
        assertEquals(productDTO.getUnitId(), updatedProduct.getUnitId());

        verify(productRepository, times(1)).findById(productDTO.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_NonExistingProduct() {
        // Arrange
        UpdateProductDTO productDTO = new UpdateProductDTO(1, "Test Product Update", "Test Description Update", 2);
        Product existingProduct = new Product();
        existingProduct.setId(2); // Different id
        existingProduct.setName("Test Product");
        existingProduct.setDescription("Test Description");
        existingProduct.setUnitId(1);

        when(productRepository.findById(productDTO.getId())).thenReturn(Optional.empty());

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
