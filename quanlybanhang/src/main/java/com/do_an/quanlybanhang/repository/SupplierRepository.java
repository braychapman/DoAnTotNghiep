package com.do_an.quanlybanhang.repository;

import com.do_an.quanlybanhang.model.entity.Category;
import com.do_an.quanlybanhang.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
    List<Supplier> findAll(); // Lấy tất cả danh mục
}
