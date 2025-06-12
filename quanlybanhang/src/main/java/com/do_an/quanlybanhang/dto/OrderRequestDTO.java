package com.do_an.quanlybanhang.dto;


import lombok.Data;

import java.util.List;
@Data

public class OrderRequestDTO {

    private String customerPhone;
    private String employeeId;
    private String paymentMethod;
    private List<OrderDetailDTO> items;

    // Getters and setters


    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderDetailDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderDetailDTO> items) {
        this.items = items;
    }
}
