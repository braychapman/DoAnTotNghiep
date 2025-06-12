package com.do_an.quanlybanhang.controller;

import com.do_an.quanlybanhang.dto.OrderRequestDTO;
import com.do_an.quanlybanhang.model.entity.Order;
import com.do_an.quanlybanhang.model.entity.Product;
import com.do_an.quanlybanhang.repository.ProductRepository;
import com.do_an.quanlybanhang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/banhang")
    public String showOrder(Model model) {
        return "banhang";
    }

    @PostMapping("/orders/hold")
    public ResponseEntity<?> holdOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            orderService.createOrder(orderRequestDTO, Order.OrderStatus.PENDING);
            return ResponseEntity.ok("Đã treo đơn hàng thành công.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi treo đơn hàng: " + e.getMessage());
        }
    }






    @GetMapping("/orders/held")
    public ResponseEntity<List<Order>> getHeldOrders() {
        return ResponseEntity.ok(orderService.getHeldOrders());
    }
    @GetMapping("/orders/held/{orderId}")
    public ResponseEntity<?> getHeldOrderDetails(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getHeldOrderDetails(orderId));
    }






}
