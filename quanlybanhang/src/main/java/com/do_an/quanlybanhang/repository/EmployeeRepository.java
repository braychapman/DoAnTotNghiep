package com.do_an.quanlybanhang.repository;

import com.do_an.quanlybanhang.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
