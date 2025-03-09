package com.example.accounting_sys.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeliveryUnitDto {
    private Long productId;
    private BigDecimal weight;
}
