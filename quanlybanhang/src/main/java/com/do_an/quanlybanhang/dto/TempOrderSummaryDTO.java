package com.do_an.quanlybanhang.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TempOrderSummaryDTO {
    private String tempOrderId;
    private String customerName;
    private LocalDateTime createdAt;
}

