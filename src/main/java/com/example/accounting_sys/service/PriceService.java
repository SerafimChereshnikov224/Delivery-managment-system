package com.example.accounting_sys.service;

import com.example.accounting_sys.exception.customException.PricePeriodNotFoundException;
import com.example.accounting_sys.model.entity.Product;
import com.example.accounting_sys.model.entity.ProductPricePeriod;
import com.example.accounting_sys.repository.ProductPricePeriodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final ProductPricePeriodRepository productPricePeriodRepository;

    private final ProductService productService;

    public BigDecimal getPrice(Product product,LocalDate startDate, LocalDate endDate) {
        return productPricePeriodRepository.findByProductAndStartDateLessThanEqualAndEndDateGreaterThanEqual(product,startDate,endDate)
                .stream()
                .findFirst()
                .map(ProductPricePeriod::getPrice)
                .orElseThrow(() -> new PricePeriodNotFoundException("cant find a price for that product"));
    }

    @Transactional
    public List<ProductPricePeriod> getProductPrices(Long id) {
        Product product = productService.getById(id);
        return productPricePeriodRepository.findByProduct(product);
    }

    @Transactional
    public ProductPricePeriod setPrice(Long id, BigDecimal newPrice) {
        Product product = productService.getById(id);

        List<ProductPricePeriod> pricePeriods = getProductPrices(product.getId());

        LocalDate startDate = LocalDate.now();
        if(!(pricePeriods.isEmpty())) {
            startDate = pricePeriods.stream()
                    .map(ProductPricePeriod::getEndDate)
                    .max(LocalDate::compareTo)
                    .orElse(LocalDate.now())
                    .plusDays(1);
        }
        LocalDate endDate = startDate.plusMonths(3).minusDays(1);

        ProductPricePeriod period = ProductPricePeriod.builder()
                .price(newPrice)
                .endDate(endDate)
                .startDate(startDate)
                .product(product)
                .build();
        return productPricePeriodRepository.save(period);

    }
}
