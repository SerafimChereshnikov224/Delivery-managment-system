package com.example.accounting_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductReportDto {
    private String productName;
    private BigDecimal totalWeight;
    private BigDecimal totalPrice;
}
