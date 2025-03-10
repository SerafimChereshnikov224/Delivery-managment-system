package com.example.accounting_sys.controller;

import com.example.accounting_sys.dto.DeliveryRequest;
import com.example.accounting_sys.dto.ReportResponse;
import com.example.accounting_sys.model.entity.Delivery;
import com.example.accounting_sys.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<String> addDelivery(@RequestBody DeliveryRequest request) {
        Delivery delivery = deliveryService.addDelivery(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("new delivery added, id: " + delivery.getId());
    }

    @GetMapping("/report")
    public ResponseEntity<ReportResponse> getDeliveryReport(@RequestParam("startDate") LocalDate startDate,
                                                            @RequestParam("endDate") LocalDate endDate
    ) {
        ReportResponse report = deliveryService.generateDeliveryReport(startDate,endDate);
        return ResponseEntity.ok(report);
    }

}
