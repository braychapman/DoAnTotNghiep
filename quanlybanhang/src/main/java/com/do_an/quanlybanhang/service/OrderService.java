package com.do_an.quanlybanhang.service;

import com.do_an.quanlybanhang.dto.OrderDetailDTO;
import com.do_an.quanlybanhang.dto.OrderRequestDTO;
import com.do_an.quanlybanhang.model.entity.*;
import com.do_an.quanlybanhang.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(OrderRequestDTO requestDTO, Order.OrderStatus status) {
        Order order = new Order();

        // Xử lý khách hàng
        String customerPhone = requestDTO.getCustomerPhone();
        if (customerPhone == null || customerPhone.isBlank()) {
            // Cho phép đơn không có khách nếu là đơn PENDING
            if (status != Order.OrderStatus.PENDING) {
                throw new RuntimeException("Đơn hàng phải có khách hàng.");
            }
            order.setCustomer(null);
        } else {
            customerRepository.findByPhoneNumber(customerPhone)
                    .ifPresentOrElse(
                            customer -> order.setCustomer(customer),
                            () -> {
                                if (status == Order.OrderStatus.PENDING) {
                                    order.setCustomer(null); // Cho phép treo đơn không có khách
                                } else {
                                    throw new RuntimeException("Không tìm thấy khách hàng.");
                                }
                            }
                    );
        }

        // Xử lý nhân viên
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên."));
        order.setEmployee(employee);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(status);
        order.setPaymentMethod(requestDTO.getPaymentMethod());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Xử lý chi tiết đơn hàng
        BigDecimal total = BigDecimal.ZERO;
        List<OrderDetail> details = new ArrayList<>();

        for (OrderDetailDTO detailDTO : requestDTO.getItems()) {
            Product product = productRepository.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(detailDTO.getQuantity());
            detail.setUnitPrice(detailDTO.getPrice());
            detail.setDiscount(BigDecimal.ZERO);

            BigDecimal lineTotal = detailDTO.getPrice().multiply(BigDecimal.valueOf(detailDTO.getQuantity()));
            total = total.add(lineTotal);

            details.add(detail);
        }

        order.setTotalAmount(total);
        orderRepository.save(order);
        orderDetailRepository.saveAll(details);

        return order;
    }




    public List<Order> getHeldOrders() {
        return orderRepository.findByStatus(Order.OrderStatus.PENDING);
    }

    public Order getHeldOrderDetails(String orderId) {
        return orderRepository.findById(orderId)
                .filter(order -> order.getStatus() == Order.OrderStatus.PENDING)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn treo"));
    }
}


