package org.chainoptim.features.product.service;

import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.ProductDTOMapper;
import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductWithStages(Integer productId) {
         return productRepository.findByIdWithStages(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
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
        return productRepository.save(ProductDTOMapper.convertCreateProductDTOToProduct(productDTO));
    }

    public Product updateProduct(UpdateProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(productDTO.getId());
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("The requested product does not exist");
        }
        Product product = productOptional.get();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setUnitId(productDTO.getUnitId());

        productRepository.save(product);

        return product;
    }

    public void deleteProduct(Integer productId) {
        Product product = new Product();
        product.setId(productId);
        productRepository.delete(product);
    }
}
