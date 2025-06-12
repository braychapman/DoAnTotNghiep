package com.do_an.quanlybanhang.service;

import com.do_an.quanlybanhang.dto.TempOrderDetailDTO;
import com.do_an.quanlybanhang.dto.TempOrderRequestDTO;
import com.do_an.quanlybanhang.model.entity.*;
import com.do_an.quanlybanhang.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TempOrderService {

    private final TempOrderRepository tempOrderRepository;
    private final TempOrderDetailRepository tempOrderDetailRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Transactional
    public void saveTempOrder(TempOrderRequestDTO request) {
        // Tạo TempOrder
        TempOrder tempOrder = new TempOrder();
        tempOrder.setTempOrderId(UUID.randomUUID().toString());

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));

        tempOrder.setCustomer(customer);
        tempOrder.setEmployee(employee);
        tempOrder.setCreatedAt(LocalDateTime.now());

        tempOrderRepository.save(tempOrder);

        // Lưu danh sách sản phẩm (TempOrderDetail)
        for (TempOrderDetailDTO dto : request.getOrderDetails()) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

            TempOrderDetail detail = new TempOrderDetail();
            detail.setTempOrder(tempOrder);
            detail.setProduct(product);
            detail.setQuantity(dto.getQuantity());
            detail.setDiscount(dto.getDiscount());
            detail.setUnitPrice(product.getPrice());

            tempOrderDetailRepository.save(detail);
        }
    }

    @Transactional
    public void convertTempOrderToRealOrder(String tempOrderId, String paymentMethod) {
        TempOrder tempOrder = tempOrderRepository.findById(tempOrderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng tạm"));

        // 1. Tạo đối tượng Order
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setCustomer(tempOrder.getCustomer());
        order.setEmployee(tempOrder.getEmployee());
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod(paymentMethod);
        order.setStatus(Order.OrderStatus.COMPLETED); // hoặc Processing nếu cần
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // 2. Tính tổng tiền
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (TempOrderDetail tempDetail : tempOrder.getDetails()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderDetailId(UUID.randomUUID().toString());
            detail.setOrder(order);
            detail.setProduct(tempDetail.getProduct());
            detail.setQuantity(tempDetail.getQuantity());
            detail.setUnitPrice(tempDetail.getUnitPrice());
            detail.setDiscount(tempDetail.getDiscount());

            BigDecimal lineTotal = tempDetail.getUnitPrice()
                    .multiply(BigDecimal.valueOf(tempDetail.getQuantity()))
                    .subtract(tempDetail.getDiscount());
            totalAmount = totalAmount.add(lineTotal);

            orderDetails.add(detail);

            // Trừ tồn kho
            Product product = tempDetail.getProduct();
            product.setStock(product.getStock() - tempDetail.getQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(totalAmount);

        // 3. Lưu vào DB
        orderRepository.save(order);
        orderDetailRepository.saveAll(orderDetails);

        // 4. Xóa đơn hàng tạm
        tempOrderDetailRepository.deleteAll(tempOrder.getDetails());
        tempOrderRepository.delete(tempOrder);
    }

}


