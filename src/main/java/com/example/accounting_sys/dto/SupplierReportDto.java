package com.example.accounting_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class SupplierReportDto {
    private String supplierName;
    private List<ProductReportDto> products;
    private BigDecimal supplierTotalPrice;
}
