package com.do_an.quanlybanhang.repository;


import com.do_an.quanlybanhang.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findAll(); // Lấy tất cả danh mục

}
