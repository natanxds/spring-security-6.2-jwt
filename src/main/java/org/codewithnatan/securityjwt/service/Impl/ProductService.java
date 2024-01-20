package org.codewithnatan.securityjwt.service.Impl;

import org.codewithnatan.securityjwt.model.ProductRequestDTO;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> save(ProductRequestDTO productRequestDTO);

    ResponseEntity<?> findAll();
}
