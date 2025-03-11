package com.example.accounting_sys.service;

import com.example.accounting_sys.dto.SupplierReportDto;
import com.example.accounting_sys.dto.DeliveryRequest;
import com.example.accounting_sys.dto.ProductReportDto;
import com.example.accounting_sys.dto.ReportResponse;
import com.example.accounting_sys.exception.customException.InvalidDataException;
import com.example.accounting_sys.model.entity.*;
import com.example.accounting_sys.repository.DeliveryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final SupplierService supplierService;
    private final ProductService productService;
    private final PriceService priceService;

    @Transactional
    public Delivery addDelivery(DeliveryRequest request) {

        if(request.getSupplierId() == null || request.getDate() == null || request.getUnits().isEmpty()) {
            throw new InvalidDataException("not enough data to create a delivery");
        }

        Supplier supplier = supplierService.getById(request.getSupplierId());

        Delivery delivery = Delivery.builder()
                .date(request.getDate())
                .supplier(supplier)
                .build();

        List<DeliveryUnit> deliveryUnits = request.getUnits().stream()
                .map(unit -> {
                    Product product = productService.getById(unit.getProductId());
                    BigDecimal price = priceService.getPrice(product,supplier,request.getDate(),request.getDate());
                    return DeliveryUnit.builder()
                            .delivery(delivery)
                            .product(product)
                            .weight(unit.getWeight())
                            .build();
                }).toList();

        delivery.setDeliveryUnits(deliveryUnits);

        return deliveryRepository.save(delivery);
    }

    @Transactional
    public ReportResponse generateDeliveryReport(LocalDate startDate, LocalDate endDate) {

        List<Delivery> deliveries = deliveryRepository.findByDateBetween(startDate, endDate);
        Map<String,List<DeliveryUnit>> unitsBySupplier = deliveries.stream()
                .flatMap(delivery -> delivery.getDeliveryUnits().stream())
                .collect(Collectors.groupingBy(unit -> unit.getDelivery().getSupplier().getName()));

        List<SupplierReportDto> supplierReports = new ArrayList<>();
        BigDecimal deliveryTotalPrice = BigDecimal.ZERO;

        for(var supplierUnits : unitsBySupplier.entrySet()) {
            String supplierName = supplierUnits.getKey();
            List<DeliveryUnit> deliveryUnits = supplierUnits.getValue();
            Supplier supplier = deliveryUnits.getFirst().getDelivery().getSupplier();

            Map<String,List<DeliveryUnit>> productsByName = deliveryUnits.stream()
                    .collect(Collectors.groupingBy(unit -> unit.getProduct().getName()));

            List<ProductReportDto> productReports = new ArrayList<>();
            BigDecimal totalPriceForSupplier = BigDecimal.ZERO;

            for(var productUnits : productsByName.entrySet()) {
                String productName = productUnits.getKey();
                List<DeliveryUnit> unitsForProduct = productUnits.getValue();

                BigDecimal totalWeightForProduct = unitsForProduct.stream()
                        .map(DeliveryUnit::getWeight)
                        .reduce(BigDecimal.ZERO,BigDecimal::add);

                BigDecimal totalPriceForProduct = BigDecimal.ZERO;
                for(var unit : unitsForProduct) {
                    LocalDate deliveryDate = unit.getDelivery().getDate();
                    Product product = unit.getProduct();
                    BigDecimal price = priceService.getPrice(product,supplier,deliveryDate,deliveryDate);
                    totalPriceForProduct = totalPriceForProduct.add(price.multiply(unit.getWeight()));
                }

                ProductReportDto productReport = new ProductReportDto(productName,totalWeightForProduct,totalPriceForProduct);
                productReports.add(productReport);

                totalPriceForSupplier = totalPriceForSupplier.add(totalPriceForProduct);
                //deliveryTotalPrice = deliveryTotalPrice.add(totalPriceForProduct);
            }
            SupplierReportDto deliveryReport = new SupplierReportDto(supplierName,productReports,totalPriceForSupplier);
            supplierReports.add(deliveryReport);
        }

        deliveryTotalPrice = supplierReports.stream()
                .map(SupplierReportDto:: getSupplierTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        return new ReportResponse(supplierReports,deliveryTotalPrice);
    }
}
