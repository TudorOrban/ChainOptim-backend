package org.chainoptim.features.product.controller;

import org.chainoptim.features.product.dto.CreateProductDTO;
import org.chainoptim.features.product.dto.ProductsSearchDTO;
import org.chainoptim.features.product.dto.UpdateProductDTO;
import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.service.ProductService;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Fetch

    @GetMapping("/organizations/{organizationId}")
    public ResponseEntity<List<ProductsSearchDTO>> getProductsByOrganizationId(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(productService.getProductsByOrganizationId(organizationId));
    }

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

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductWithStages(@PathVariable Integer productId) {
        Product product = productService.getProductWithStages(productId);
        return ResponseEntity.ok(product);
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDTO productDTO) {
        Product product = productService.createProduct(productDTO);
        return ResponseEntity.ok(product);
    }

    // Update
    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody UpdateProductDTO productDTO) {
        Product product = productService.updateProduct(productDTO);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
