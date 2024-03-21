package org.chainoptim.features.product.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.ProductDTOMapper;
import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, EntitySanitizerService entitySanitizerService) {
        this.productRepository = productRepository;
        this.entitySanitizerService = entitySanitizerService;
    }

    public Product getProductWithStages(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + productId + " not found."));

        // Trigger lazy loading
        product.getStages().forEach(stage -> {
            Hibernate.initialize(stage.getStageInputs());
            Hibernate.initialize(stage.getStageOutputs());
        });

        return product;
    }

    public List<ProductsSearchDTO> getProductsByOrganizationIdSmall(Integer organizationId) {
        return productRepository.findByOrganizationIdSmall(organizationId);
    }

    public List<ProductsSearchDTO> getProductsByOrganizationId(Integer organizationId) {
        List<Product> products = productRepository.findByOrganizationId(organizationId);
        return products.stream()
                .map(ProductDTOMapper::convertToProductsSearchDTO)
                .toList();
    }

    public PaginatedResults<ProductsSearchDTO> getProductsByOrganizationIdAdvanced(Integer organizationId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage) {
        PaginatedResults<Product> paginatedResults = productRepository.findByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return new PaginatedResults<>(
                paginatedResults.results.stream()
                        .map(ProductDTOMapper::convertToProductsSearchDTO)
                        .toList(),
                paginatedResults.totalCount
        );
    }

    public Product createProduct(CreateProductDTO productDTO) {
        CreateProductDTO sanitizedProductDTO = entitySanitizerService.sanitizeCreateProductDTO(productDTO);
        return productRepository.save(ProductDTOMapper.convertCreateProductDTOToProduct(sanitizedProductDTO));
    }

    public Product updateProduct(UpdateProductDTO productDTO) {
        UpdateProductDTO sanitizedProductDTO = entitySanitizerService.sanitizeUpdateProductDTO(productDTO);
        Product product = productRepository.findById(sanitizedProductDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + sanitizedProductDTO.getId() + " not found."));

        product.setName(sanitizedProductDTO.getName());
        product.setDescription(sanitizedProductDTO.getDescription());
        product.setUnitId(sanitizedProductDTO.getUnitId());

        productRepository.save(product);

        return product;
    }

    public void deleteProduct(Integer productId) {
        Product product = new Product();
        product.setId(productId);
        productRepository.delete(product);
    }
}
