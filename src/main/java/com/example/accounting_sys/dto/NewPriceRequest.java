package com.example.accounting_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class NewPriceRequest {
    private BigDecimal newPrice;
    private Long supplierId;
}
