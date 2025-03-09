package com.example.accounting_sys.dto;

import com.example.accounting_sys.model.entity.DeliveryUnit;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DeliveryRequest {
    private Long supplierId;
    private LocalDate date;
    private List<DeliveryUnitDto> units;
}
