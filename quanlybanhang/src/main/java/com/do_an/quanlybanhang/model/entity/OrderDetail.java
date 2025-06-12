package com.do_an.quanlybanhang.model.entity;


import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ORDER_DETAIL")
public class OrderDetail {

    @Id
    @Column(name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderDetailId;  // UUID hoặc String tùy theo yêu cầu của bạn

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  // Liên kết với entity Order

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // Liên kết với entity Product

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;


}
