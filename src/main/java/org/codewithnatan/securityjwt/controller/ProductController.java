package org.codewithnatan.securityjwt.controller;

import lombok.RequiredArgsConstructor;
import org.codewithnatan.securityjwt.model.ProductRequestDTO;
import org.codewithnatan.securityjwt.service.Impl.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.save(productRequestDTO);
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        return productService.findAll();
    }
}
