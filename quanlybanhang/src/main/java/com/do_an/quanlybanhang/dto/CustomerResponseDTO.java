package com.do_an.quanlybanhang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private String name;
    private int discountPercent;  // Giả sử bạn dùng điểm để tính % giảm giá
}
