package com.do_an.quanlybanhang.repository;

import com.do_an.quanlybanhang.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByPhoneNumber(String phoneNumber);

}
