package com.example.accounting_sys.repository;

import com.example.accounting_sys.model.entity.Product;
import com.example.accounting_sys.model.entity.ProductPricePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductPricePeriodRepository extends JpaRepository<ProductPricePeriod,Long> {

    List<ProductPricePeriod> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

    List<ProductPricePeriod> findByProductAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Product product, LocalDate startDate, LocalDate endDate);

    List<ProductPricePeriod> findByProduct(Product product);
}
