package com.do_an.quanlybanhang.dto;

import lombok.Data;

import java.util.List;

@Data
public class TempOrderRequestDTO {

    private String customerId; // ID khách hàng
    private String employeeId; // ID nhân viên
    private List<TempOrderDetailDTO> orderDetails; // Danh sách sản phẩm trong đơn tạm

    // Getters, setters
}

