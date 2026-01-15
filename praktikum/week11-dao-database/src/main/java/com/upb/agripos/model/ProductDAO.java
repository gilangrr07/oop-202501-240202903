package com.upb.agripos.model;

import java.util.List;

import com.upb.agripos.dao.Product;

public interface ProductDAO {
    void insert(Product product) throws Exception;
    void update(Product product) throws Exception;
    Product findByCode(String code) throws Exception;
    List<Product> findAll() throws Exception;
    void delete(String code) throws Exception;
}
