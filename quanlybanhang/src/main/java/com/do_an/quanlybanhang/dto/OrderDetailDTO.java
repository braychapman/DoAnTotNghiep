package com.do_an.quanlybanhang.dto;


import java.math.BigDecimal;

public class OrderDetailDTO {
    private String productId;
    private int quantity;
    private BigDecimal price;

    // Getters and setters

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
