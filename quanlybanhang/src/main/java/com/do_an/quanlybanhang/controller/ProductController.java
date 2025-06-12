package com.do_an.quanlybanhang.controller;


import com.do_an.quanlybanhang.dto.ProductResponseDTO;
import com.do_an.quanlybanhang.model.entity.Category;
import com.do_an.quanlybanhang.model.entity.Product;
import com.do_an.quanlybanhang.repository.CategoryRepository;
import com.do_an.quanlybanhang.repository.ProductRepository;
import com.do_an.quanlybanhang.repository.SupplierRepository;
import com.do_an.quanlybanhang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/sanpham")
    public String getProducts(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "category", required = false) String category,
                              @RequestParam(value = "status", required = false) String status,
                              Model model) {

        List<Category> categories = categoryRepository.findAll(); // Lấy danh mục từ bảng Category
        model.addAttribute("categories", categories); // Truyền danh mục vào model

        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productRepository.findByNameContainingIgnoreCaseOrBarcodeContainingIgnoreCase(keyword, keyword);
        } else {
            products = productRepository.findAll();
        }

        // Lọc theo danh mục nếu có
        if (category != null && !category.isEmpty() && !category.equals("Tất cả")) {
            products = products.stream()
                    .filter(product -> product.getCategory().getName().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }

        // Lọc theo tình trạng nếu có
        if (status != null && !status.isEmpty() && !status.equals("Tất cả")) {
            products = products.stream()
                    .filter(product -> {
                        if (status.equals("Còn hàng")) {
                            return product.getStock() > 5;
                        } else if (status.equals("Sắp hết")) {
                            return product.getStock() <= 5 && product.getStock() > 0;
                        } else if (status.equals("Hết hàng")) {
                            return product.getStock() == 0;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
        }

        model.addAttribute("products", products);
        return "sanpham"; // Tên của trang HTML
    }

    // Phương thức để lấy thông tin sản phẩm cần sửa
    @GetMapping("/sanpham/sua/{id}")
    public String getEditProduct(@PathVariable("id") String id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            List<Category> categories = productService.getAllCategories(); // Lấy tất cả danh mục từ service
            model.addAttribute("product", product.get());
            model.addAttribute("categories", categories); // Truyền danh mục sản phẩm vào model
            return "editProduct"; // Tên của trang HTML để chỉnh sửa
        }
        return "redirect:/sanpham"; // Nếu không tìm thấy sản phẩm, quay lại trang danh sách
    }

    // Phương thức để lưu thông tin sản phẩm sau khi sửa
    @PostMapping("/sanpham/sua/{id}")
    public String postEditProduct(@PathVariable("id") String id, @ModelAttribute Product product) {
        Optional<Product> existingProduct = productService.getProductById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setBarcode(product.getBarcode());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setStock(product.getStock());
            updatedProduct.setCategory(product.getCategory());
            updatedProduct.setImageUrl(product.getImageUrl());
            productService.updateProduct(updatedProduct); // Gọi service để lưu sản phẩm đã cập nhật
            return "redirect:/sanpham"; // Quay lại trang danh sách sản phẩm
        }
        return "redirect:/sanpham"; // Nếu không tìm thấy sản phẩm, quay lại trang danh sách
    }

    // Phương thức hiển thị form thêm sản phẩm
    @GetMapping("/sanpham/them")
    public String showAddProductForm(Model model) {
        List<Category> categories = categoryRepository.findAll(); // Lấy tất cả danh mục
        model.addAttribute("categories", categories); // Truyền danh mục vào model
        model.addAttribute("product", new Product()); // Thêm đối tượng product vào model để Thymeleaf bind form
        model.addAttribute("suppliers", supplierRepository.findAll()); //
        return "addProduct"; // Tên của trang HTML để thêm sản phẩm
    }

    // Phương thức xử lý dữ liệu thêm sản phẩm
    @PostMapping("/sanpham/them")
    public String addProduct(@ModelAttribute Product product) {
        productService.saveProduct(product); // Lưu sản phẩm mới vào cơ sở dữ liệu
        return "redirect:/sanpham"; // Quay lại danh sách sản phẩm
    }


    @PostMapping("/sanpham/xoa/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return "redirect:/sanpham";
    }

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/api/products/search")
    @ResponseBody
    public List<ProductResponseDTO> searchProducts(@RequestParam String keyword) {
        return productRepository
                .findByNameContainingIgnoreCaseOrBarcode(keyword, keyword)
                .stream()
                .map(p -> new ProductResponseDTO(
                        p.getProductId(),
                        p.getName(),
                        p.getPrice(),
                        p.getStock()
                ))
                .collect(Collectors.toList());
    }



}

