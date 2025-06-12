package com.do_an.quanlybanhang.repository;

import com.do_an.quanlybanhang.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByStatus(Order.OrderStatus status);

}
