package com.do_an.quanlybanhang.model.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String employeeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;  // Enum để lưu vai trò nhân viên

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    // Enum để lưu vai trò nhân viên
    public enum Role {
        ADMIN,
        MANAGER,
        SALESPERSON
    }

}
