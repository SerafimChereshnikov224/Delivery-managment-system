package com.example.accounting_sys.service;

import com.example.accounting_sys.exception.customException.ProductNotFoundException;
import com.example.accounting_sys.model.entity.Product;
import com.example.accounting_sys.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("product does not exist, id; " + id));
    }
}
