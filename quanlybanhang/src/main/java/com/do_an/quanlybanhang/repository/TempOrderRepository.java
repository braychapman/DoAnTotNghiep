package com.do_an.quanlybanhang.repository;

import com.do_an.quanlybanhang.model.entity.TempOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TempOrderRepository extends JpaRepository<TempOrder, String> {

    // Tìm đơn treo theo customer
    List<TempOrder> findByCustomerCustomerId(String customerId);

    // Hoặc theo số điện thoại (nếu cần)
    List<TempOrder> findByCustomerPhoneNumber(String phoneNumber);

    // Tìm theo employee
    List<TempOrder> findByEmployeeEmployeeId(String employeeId);

    // Tìm đơn treo cụ thể theo ID (đã có sẵn từ JpaRepository)
    Optional<TempOrder> findById(String tempOrderId);
}
