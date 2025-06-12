package com.do_an.quanlybanhang.model.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Entity
@Data
@Table(name = "ORDERS")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;  // UUID hoặc String tùy theo yêu cầu của bạn

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;  // Liên kết với entity Customer

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;  // Liên kết với entity Employee

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;  // Enum để lưu trạng thái đơn hàng

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // Enum để lưu trạng thái đơn hàng
    public enum OrderStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        CANCELLED
    }


}

