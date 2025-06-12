package com.do_an.quanlybanhang.repository;

import com.do_an.quanlybanhang.model.entity.TempOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempOrderDetailRepository extends JpaRepository<TempOrderDetail, String> {

    // Tìm tất cả chi tiết của một đơn treo
    List<TempOrderDetail> findByTempOrderTempOrderId(String tempOrderId);
}
