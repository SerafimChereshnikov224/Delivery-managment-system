package com.example.accounting_sys.repository;

import com.example.accounting_sys.model.entity.DeliveryUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryUnitRepository extends JpaRepository<DeliveryUnit,Long> {
}
