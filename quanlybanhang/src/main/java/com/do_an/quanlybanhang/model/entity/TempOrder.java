package com.do_an.quanlybanhang.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

@Table(name = "temp_orders")
public class TempOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName; // Thay thế cho customerId

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String status;

    @OneToMany(mappedBy = "tempOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TempOrderDetail> orderDetails = new ArrayList<>();

    // getters/setters
}

