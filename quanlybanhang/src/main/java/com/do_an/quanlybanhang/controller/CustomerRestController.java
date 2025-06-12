package com.do_an.quanlybanhang.controller;

import com.do_an.quanlybanhang.dto.CustomerResponseDTO;
import com.do_an.quanlybanhang.model.entity.Customer;
import com.do_an.quanlybanhang.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/find-by-phone")
    public ResponseEntity<CustomerResponseDTO> findCustomerByPhone(@RequestParam String phone) {
        return customerRepository.findByPhoneNumber(phone)
                .map(customer -> {
                    int discount = calculateDiscount(customer.getPoints());
                    CustomerResponseDTO dto = new CustomerResponseDTO(customer.getName(), discount);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    private int calculateDiscount(int points) {
        if (points >= 1000) return 10;
        if (points >= 500) return 5;
        return 0;
    }

    @PostMapping("/create")
    public CustomerResponseDTO createCustomer(@RequestBody Customer customer) {
        customer.setCustomerId(UUID.randomUUID().toString());
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setPoints(0); // mặc định 0 điểm

        Customer saved = customerRepository.save(customer);
        return new CustomerResponseDTO(saved.getName(), 0);
    }

}
