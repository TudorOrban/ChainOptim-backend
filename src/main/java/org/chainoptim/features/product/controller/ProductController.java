package org.chainoptim.features.product.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.service.ProductService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final SecurityService securityService;

    @Autowired
    public ProductController(
            ProductService productService,
            SecurityService securityService
    ) {
        this.productService = productService;
        this.securityService = securityService;
    }

    // Fetch
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Product\", \"Read\")")
    @GetMapping("/organizations/{organizationId}/small")
    public ResponseEntity<List<ProductsSearchDTO>> getProductsByOrganizationIdSmall(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(productService.getProductsByOrganizationIdSmall(organizationId));
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Product\", \"Read\")")
    @GetMapping("/organizations/{organizationId}")
    public ResponseEntity<List<ProductsSearchDTO>> getProductsByOrganizationId(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(productService.getProductsByOrganizationId(organizationId));
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Product\", \"Read\")")
    @GetMapping("/organizations/advanced/{organizationId}")
    public ResponseEntity<PaginatedResults<ProductsSearchDTO>> getProductsByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "ascending", required = false, defaultValue = "true") boolean ascending,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "itemsPerPage", required = false, defaultValue = "30") int itemsPerPage
    ) {
        PaginatedResults<ProductsSearchDTO> paginatedResults = productService.getProductsByOrganizationIdAdvanced(organizationId, searchQuery, sortBy, ascending, page, itemsPerPage);
        return ResponseEntity.ok(paginatedResults);
    }

    @PreAuthorize("@securityService.canAccessEntity(#productId, \"Product\", \"Read\")")
    @GetMapping("/{productId}/stages")
    public ResponseEntity<Product> getProductWithStages(@PathVariable Integer productId) {
        Product product = productService.getProductWithStages(productId);
        return ResponseEntity.ok(product);
    }

    // Create
    @PreAuthorize("@securityService.canAccessOrganizationEntity(#productDTO.getOrganizationId(), \"Product\", \"Create\")")
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDTO productDTO) {
        Product product = productService.createProduct(productDTO);
        return ResponseEntity.ok(product);
    }

    // Update
    @PreAuthorize("@securityService.canAccessEntity(#productDTO.getId(), \"Product\", \"Update\")")
    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody UpdateProductDTO productDTO) {
        Product product = productService.updateProduct(productDTO);
        return ResponseEntity.ok(product);
    }

    // Delete
    @PreAuthorize("@securityService.canAccessEntity(#productId, \"Product\", \"Delete\")")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}
