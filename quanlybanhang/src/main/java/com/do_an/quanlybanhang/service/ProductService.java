package com.do_an.quanlybanhang.service;




import com.do_an.quanlybanhang.model.entity.Category;
import com.do_an.quanlybanhang.model.entity.Product;
import com.do_an.quanlybanhang.repository.CategoryRepository;
import com.do_an.quanlybanhang.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  CategoryRepository categoryRepository;
    //lay danh sach tat ca san pham
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    //tim kiem theo ma/ten san pham
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseOrBarcodeContainingIgnoreCase(keyword, keyword);
    }


    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Cập nhật thông tin sản phẩm
    public Product updateProduct(Product product) {
        return productRepository.save(product); // Lưu sản phẩm đã cập nhật
    }



    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
    public Optional<Product> getProductByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }



}

