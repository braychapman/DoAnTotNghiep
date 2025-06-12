package com.do_an.quanlybanhang.repository;



import com.do_an.quanlybanhang.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // Tìm sản phẩm có tên hoặc mã sản phẩm chứa từ khóa
    List<Product> findByNameContainingIgnoreCaseOrBarcodeContainingIgnoreCase(String keyword, String keyword2);
    boolean existsByCategory_CategoryId(String categoryId);
    Optional<Product> findByBarcode(String barcode);
    List<Product> findByNameContainingIgnoreCaseOrBarcode(String name, String barcode);



}


