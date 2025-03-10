package com.example.accounting_sys.service;

import com.example.accounting_sys.exception.customException.SupplierNotFoundException;
import com.example.accounting_sys.model.entity.Supplier;
import com.example.accounting_sys.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Transactional
    public Supplier getById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("supplier does not exist, id: " + id));
    }

}
