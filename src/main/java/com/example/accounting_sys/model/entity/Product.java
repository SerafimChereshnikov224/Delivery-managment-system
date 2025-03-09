package com.example.accounting_sys.model.entity;

import com.example.accounting_sys.model.type.ProductType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "product_type")
    @Enumerated(value = EnumType.STRING)
    private ProductType type;
}
