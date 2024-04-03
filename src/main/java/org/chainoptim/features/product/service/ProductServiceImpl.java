package org.chainoptim.features.product.service;

import org.chainoptim.core.subscriptionplan.service.SubscriptionPlanLimiterService;
import org.chainoptim.exception.PlanLimitReachedException;
import org.chainoptim.exception.ResourceNotFoundException;
import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.ProductDTOMapper;
import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.features.product.repository.ProductRepository;
import org.chainoptim.shared.sanitization.EntitySanitizerService;
import org.chainoptim.shared.search.model.PaginatedResults;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UnitOfMeasurementService unitOfMeasurementService;
    private final SubscriptionPlanLimiterService planLimiterService;
    private final EntitySanitizerService entitySanitizerService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              UnitOfMeasurementService unitOfMeasurementService,
                              SubscriptionPlanLimiterService planLimiterService,
                              EntitySanitizerService entitySanitizerService) {
        this.productRepository = productRepository;
        this.unitOfMeasurementService = unitOfMeasurementService;
        this.planLimiterService = planLimiterService;
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
        // Check if plan limit is reached
        if (planLimiterService.isLimitReached(productDTO.getOrganizationId(), "Products")) {
            throw new PlanLimitReachedException("You have reached the limit of allowed products for the current Subscription Plan.");
        }

        // Sanitize input
        CreateProductDTO sanitizedProductDTO = entitySanitizerService.sanitizeCreateProductDTO(productDTO);

        // Create unit of measurement if requested
        if (sanitizedProductDTO.isCreateUnit() && sanitizedProductDTO.getUnitDTO() != null) {
            UnitOfMeasurement unitOfMeasurement = unitOfMeasurementService.createUnitOfMeasurement(sanitizedProductDTO.getUnitDTO());
            Product product = ProductDTOMapper.convertCreateProductDTOToProduct(sanitizedProductDTO);
            product.setUnit(unitOfMeasurement);
            return productRepository.save(product);
        } else {
            return productRepository.save(ProductDTOMapper.convertCreateProductDTOToProduct(sanitizedProductDTO));
        }
    }

    public Product updateProduct(UpdateProductDTO productDTO) {
        UpdateProductDTO sanitizedProductDTO = entitySanitizerService.sanitizeUpdateProductDTO(productDTO);
        Product product = productRepository.findById(sanitizedProductDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + sanitizedProductDTO.getId() + " not found."));

        product.setName(sanitizedProductDTO.getName());
        product.setDescription(sanitizedProductDTO.getDescription());
        UnitOfMeasurement unit = new UnitOfMeasurement();
        unit.setId(sanitizedProductDTO.getUnitId());
        product.setUnit(unit);

        productRepository.save(product);

        return product;
    }

    public void deleteProduct(Integer productId) {
        Product product = new Product();
        product.setId(productId);
        productRepository.delete(product);
    }
}
