package com.upb.agripos.controller;

import java.util.List;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;



public class ProductController {

    private ProductService service = new ProductService();

    public void tambahProduk(Product p) {
        service.tambahProduk(p);
    }

    public List<Product> loadProduk() {
        return service.getProduk();
    }
}
