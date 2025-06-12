package com.do_an.quanlybanhang.controller;

import com.do_an.quanlybanhang.model.entity.Supplier;
import com.do_an.quanlybanhang.repository.SupplierRepository;
import com.do_an.quanlybanhang.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nhacungcap")
public class SupplierController {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierService supplierService;

    // Hiển thị danh sách nhà cung cấp
    @GetMapping
    public String showSuppliers(Model model) {
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "nhacungcap";  // Trả về trang HTML hiển thị danh sách nhà cung cấp
    }

    // Thêm nhà cung cấp
    @PostMapping("/them")
    public String addSupplier(@ModelAttribute Supplier supplier) {
        supplierRepository.save(supplier);
        return "redirect:/nhacungcap";  // Sau khi thêm, điều hướng về lại trang danh sách
    }

    // Sửa nhà cung cấp
    @PostMapping("/sua")
    public String updateSupplier(@ModelAttribute Supplier supplier) {
        supplierRepository.save(supplier);
        return "redirect:/nhacungcap";  // Sau khi cập nhật, điều hướng về lại trang danh sách
    }

    // Xóa nhà cung cấp
    @PostMapping("/xoa/{supplierId}")
    public String deleteSupplier(@PathVariable String supplierId) {
        supplierService.delete(supplierId);
        return "redirect:/nhacungcap";  // Sau khi xóa, điều hướng về lại trang danh sách
    }
}
