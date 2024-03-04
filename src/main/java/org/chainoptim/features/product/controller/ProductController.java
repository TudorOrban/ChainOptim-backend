package org.chainoptim.features.product.controller;

import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductWithStages(@PathVariable Integer productId) {
        Product product = productService.getProductWithStages(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/organizations/{organizationId}")
    public ResponseEntity<?> getProductsByOrganizationId(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(productService.getProductsByOrganizationId(organizationId));
    }

    @GetMapping("/organizations/advanced/{organizationId}")
    public ResponseEntity<?> getProductsByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "searchQuery", required = false) String searchQuery
    ) {
        return ResponseEntity.ok(productService.getProductsByOrganizationIdAdvanced(organizationId, searchQuery));
    }
}
