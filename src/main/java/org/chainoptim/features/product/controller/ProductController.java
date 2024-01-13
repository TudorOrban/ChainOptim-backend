package org.chainoptim.features.product.controller;

import org.chainoptim.features.product.model.Product;
import org.chainoptim.features.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductWithStages(@PathVariable Integer productId) {
        Product product = productService.getProductWithStages(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/organizations/{organizationId}")
    public ResponseEntity<?> getProductsByOrganization(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(productService.getProductsByOrganization(organizationId));
    }
}
