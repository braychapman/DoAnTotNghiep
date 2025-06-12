package com.do_an.quanlybanhang.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "TEMP_ORDER_DETAIL")
public class TempOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "temp_order_detail_id")
    private String tempOrderDetailId;

    @ManyToOne
    @JoinColumn(name = "temp_order_id", nullable = false)
    private TempOrder tempOrder;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;
}
