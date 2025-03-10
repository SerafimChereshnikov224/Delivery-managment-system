package com.example.accounting_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PriceListResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
}
