package com.example.accounting_sys.controller;

import com.example.accounting_sys.dto.NewPriceRequest;
import com.example.accounting_sys.model.entity.ProductPricePeriod;
import com.example.accounting_sys.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;

    @PostMapping("/{productId}/quarter-price")
    public ResponseEntity<String> setNextQuarterPrice(@PathVariable("productId") Long id,
                                                      @RequestBody NewPriceRequest request
    ) {
        ProductPricePeriod newPeriod = priceService.setPrice(id,request.getNewPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body("quarter price added for product: " + newPeriod.getProduct().getName());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductPricePeriod>> getProductPrices(@PathVariable("productId") Long id) {
        List<ProductPricePeriod> prices = priceService.getProductPrices(id);
        return prices.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : ResponseEntity.ok(prices);
    }
}
