package com.do_an.quanlybanhang.dto;


import lombok.Data;

import java.math.BigDecimal;
@Data

public class ProductResponseDTO {
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer stock;

    public ProductResponseDTO(String productId, String name, BigDecimal price, Integer stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }


}

