package org.codewithnatan.securityjwt.service;

import lombok.RequiredArgsConstructor;
import org.codewithnatan.securityjwt.model.Product;
import org.codewithnatan.securityjwt.model.ProductRequestDTO;
import org.codewithnatan.securityjwt.repository.ProductRepository;
import org.codewithnatan.securityjwt.service.Impl.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<?> save(ProductRequestDTO productRequestDTO) {
        if (productRepository.findByName(productRequestDTO.name()).isPresent()) {
            return ResponseEntity.badRequest().body("Product already exists");
        }

        Product product = new Product();
        product.setName(productRequestDTO.name());
        product.setDescription(productRequestDTO.description());
        product.setPrice(productRequestDTO.price());

        return ResponseEntity.ok(productRepository.save(product));
    }

    @Override
    public ResponseEntity<?> findAll() {

        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            return ResponseEntity.badRequest().body("No products found");
        }

        return ResponseEntity.ok(products);
    }
}
