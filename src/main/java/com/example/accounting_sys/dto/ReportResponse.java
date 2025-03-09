package com.example.accounting_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ReportResponse {
    private List<SupplierReportDto> deliveryReportResponses;
    private BigDecimal deliveryTotalPrice;
}
