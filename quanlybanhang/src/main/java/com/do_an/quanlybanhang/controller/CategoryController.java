package com.do_an.quanlybanhang.controller;

import com.do_an.quanlybanhang.model.entity.Category;
import com.do_an.quanlybanhang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Hiển thị danh mục
    @GetMapping("/danhmuc")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "danhmuc";
    }

    // Xóa danh mục
    @PostMapping("/danhmuc/xoa/{id}")
    public String deleteCategory(@PathVariable("id") String categoryId, Model model) {
        try {
            categoryService.deleteCategoryById(categoryId);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Không thể xóa danh mục này vì có sản phẩm liên quan.");
        }
        return "redirect:/danhmuc";
    }

    // Xử lý sửa danh mục
    @PostMapping("/danhmuc/sua")
    public String updateCategory(@RequestParam String categoryId,
                                 @RequestParam String name,
                                 @RequestParam String description,
                                 Model model) {
        try {
            categoryService.updateCategory(categoryId, name, description);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Cập nhật danh mục thất bại: " + e.getMessage());
        }
        return "redirect:/danhmuc";
    }
    // Thêm danh mục
    @PostMapping("/danhmuc/them")
    public String addCategory(@RequestParam String name,
                              @RequestParam String description,
                              Model model) {
        try {
            categoryService.addCategory(name, description);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Thêm danh mục thất bại: " + e.getMessage());
        }
        return "redirect:/danhmuc";
    }

}
