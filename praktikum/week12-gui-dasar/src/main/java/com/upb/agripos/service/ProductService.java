package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

public class ProductService {

    private ProductDAO dao = new ProductDAO();

    public void tambahProduk(Product p) {
        dao.insert(p);
    }

    public List<Product> getProduk() {
        return dao.findAll();
    }
}
