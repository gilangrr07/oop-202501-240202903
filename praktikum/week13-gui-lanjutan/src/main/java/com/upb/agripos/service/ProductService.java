package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

public class ProductService {

    private final ProductDAO dao = new ProductDAO();


    public List<Product> findAll() {
        return dao.findAll();
    }

    // ======================
    // CREATE
    // ======================
    public void add(Product product) {
        dao.insert(product);   // 🔥 BUKAN save()
    }

    // ======================
    // DELETE
    // ======================
    public void delete(String code) {
        dao.delete(code);
    }
}
