package com.do_an.quanlybanhang.service;

import com.do_an.quanlybanhang.model.entity.Category;
import com.do_an.quanlybanhang.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả danh mục
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Cập nhật thông tin danh mục
    @Transactional
    public void updateCategory(String categoryId, String name, String description) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));

        category.setName(name);
        category.setDescription(description);
        categoryRepository.save(category);
    }

    // Thêm danh mục mới
    @Transactional
    public void addCategory(String name, String description) {
        Category category = new Category();
        category.setCategoryId(UUID.randomUUID().toString());  // Tạo id ngẫu nhiên
        category.setName(name);
        category.setDescription(description);
        categoryRepository.save(category);
    }

    // Xóa danh mục
    @Transactional
    public void deleteCategoryById(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
