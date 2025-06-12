package com.do_an.quanlybanhang.controller;

import com.do_an.quanlybanhang.dto.TempOrderDetailDTO;
import com.do_an.quanlybanhang.dto.TempOrderRequestDTO;
import com.do_an.quanlybanhang.dto.TempOrderSummaryDTO;
import com.do_an.quanlybanhang.model.entity.TempOrder;
import com.do_an.quanlybanhang.repository.TempOrderRepository;
import com.do_an.quanlybanhang.service.OrderService;
import com.do_an.quanlybanhang.service.TempOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/temp-orders")
@RequiredArgsConstructor
public class TempOrderController {

    private final TempOrderService tempOrderService;
    @Autowired
    private TempOrderRepository tempOrderRepository;
    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTempOrder(@RequestBody TempOrderRequestDTO request) {
        tempOrderService.saveTempOrder(request);
        return ResponseEntity.ok("Đơn hàng tạm thời đã được lưu");
    }


    @GetMapping("/list")
    public ResponseEntity<List<TempOrderSummaryDTO>> getAllTempOrders() {
        List<TempOrder> tempOrders = tempOrderRepository.findAll();

        List<TempOrderSummaryDTO> summaries = tempOrders.stream().map(order -> {
            TempOrderSummaryDTO dto = new TempOrderSummaryDTO();
            dto.setTempOrderId(order.getTempOrderId());
            dto.setCustomerName(order.getCustomer().getName());
            dto.setCreatedAt(order.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(summaries);
    }
    @GetMapping("/{tempOrderId}")
    public ResponseEntity<TempOrderRequestDTO> getTempOrderById(@PathVariable String tempOrderId) {
        TempOrder tempOrder = tempOrderRepository.findById(tempOrderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng tạm"));

        TempOrderRequestDTO dto = new TempOrderRequestDTO();
        dto.setCustomerId(tempOrder.getCustomer().getCustomerId());
        dto.setEmployeeId(tempOrder.getEmployee().getEmployeeId());

        List<TempOrderDetailDTO> detailDTOs = tempOrder.getDetails().stream().map(detail -> {
            TempOrderDetailDTO d = new TempOrderDetailDTO();
            d.setProductId(detail.getProduct().getProductId());
            d.setQuantity(detail.getQuantity());
            d.setUnitPrice(detail.getUnitPrice());
            d.setDiscount(detail.getDiscount());
            return d;
        }).collect(Collectors.toList());

        dto.setOrderDetails(detailDTOs);

        return ResponseEntity.ok(dto);
    }
    @PostMapping("/temp-orders/{tempOrderId}/checkout")
    public ResponseEntity<?> checkoutTempOrder(
            @PathVariable String tempOrderId,
            @RequestParam String paymentMethod) {
        tempOrderService.convertTempOrderToRealOrder(tempOrderId, paymentMethod);
        return ResponseEntity.ok("Thanh toán thành công và đã chuyển đơn tạm thành đơn hàng");
    }


}

