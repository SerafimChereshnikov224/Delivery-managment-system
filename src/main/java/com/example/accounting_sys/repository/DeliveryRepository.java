package com.example.accounting_sys.repository;

import com.example.accounting_sys.model.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    List<Delivery> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
