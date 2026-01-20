package com.upb.agripos.dao;

import java.util.List;

import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.model.Product;

/**
 * ProductDAO - Interface untuk Data Access Object
 * Integrasi: Bab 6 (SOLID - DIP), Bab 11 (DAO Pattern)
 */
public interface ProductDAO {
    void insert(Product product) throws DatabaseException;
    void update(Product product) throws DatabaseException;
    void delete(String code) throws DatabaseException;
    Product findByCode(String code) throws DatabaseException;
    List<Product> findAll() throws DatabaseException;
}

