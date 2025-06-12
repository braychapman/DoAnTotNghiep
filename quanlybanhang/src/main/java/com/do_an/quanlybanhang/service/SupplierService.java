package com.do_an.quanlybanhang.service;

import com.do_an.quanlybanhang.model.entity.Supplier;
import com.do_an.quanlybanhang.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public void delete(String supplierId) {
        supplierRepository.deleteById(supplierId);
    }
}
