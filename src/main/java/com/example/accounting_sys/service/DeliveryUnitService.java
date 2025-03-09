package com.example.accounting_sys.service;

import com.example.accounting_sys.repository.DeliveryUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryUnitService {

    private final DeliveryUnitRepository deliveryUnitRepository;
}
