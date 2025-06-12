package com.do_an.quanlybanhang.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class TempOrderDetailDTO {
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId; // ID sản phẩm
    private int quantity;     // Số lượng sản phẩm
    private BigDecimal discount; // Mức giảm giá (nếu có)
    private BigDecimal unitPrice;

    // Getters, setters
}
