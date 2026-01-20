package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.exception.ProductNotFoundException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Product;

/**
 * ProductService - Business Logic Layer
 * Integrasi: Bab 6 (SOLID - SRP, DIP), Bab 9 (Exception), Bab 11 (DAO)
 */
public class ProductService {
    
    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new JdbcProductDAO();
    }

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Update stok produk ke database
     * Digunakan setelah checkout untuk sync database
     */
    public void updateStock(Product product) throws DatabaseException, ValidationException {
        if (product == null) {
            throw new ValidationException("Produk tidak boleh null!");
        }
        
        if (product.getStock() < 0) {
            throw new ValidationException("Stok tidak boleh negatif!");
        }
        
        productDAO.update(product);
    }

    public void addProduct(Product product) throws ValidationException, DatabaseException {
        // Validasi
        validateProduct(product);
        
        // Cek duplikasi
        Product existing = productDAO.findByCode(product.getCode());
        if (existing != null) {
            throw new ValidationException("Produk dengan kode " + product.getCode() + " sudah ada!");
        }
        
        productDAO.insert(product);
    }

    public void updateProduct(Product product) throws ValidationException, DatabaseException, ProductNotFoundException {
        validateProduct(product);
        
        Product existing = productDAO.findByCode(product.getCode());
        if (existing == null) {
            throw new ProductNotFoundException("Produk dengan kode " + product.getCode() + " tidak ditemukan!");
        }
        
        productDAO.update(product);
    }

    public void deleteProduct(String code) throws ValidationException, DatabaseException, ProductNotFoundException {
        if (code == null || code.trim().isEmpty()) {
            throw new ValidationException("Kode produk tidak boleh kosong!");
        }
        
        Product existing = productDAO.findByCode(code);
        if (existing == null) {
            throw new ProductNotFoundException("Produk dengan kode " + code + " tidak ditemukan!");
        }
        
        productDAO.delete(code);
    }

    public Product getProductByCode(String code) throws DatabaseException, ProductNotFoundException {
        Product product = productDAO.findByCode(code);
        if (product == null) {
            throw new ProductNotFoundException("Produk dengan kode " + code + " tidak ditemukan!");
        }
        return product;
    }

    public List<Product> getAllProducts() throws DatabaseException {
        return productDAO.findAll();
    }

    private void validateProduct(Product product) throws ValidationException {
        if (product == null) {
            throw new ValidationException("Produk tidak boleh null!");
        }
        
        if (product.getCode() == null || product.getCode().trim().isEmpty()) {
            throw new ValidationException("Kode produk tidak boleh kosong!");
        }
        
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ValidationException("Nama produk tidak boleh kosong!");
        }
        
        if (product.getPrice() <= 0) {
            throw new ValidationException("Harga harus lebih dari 0!");
        }
        
        if (product.getStock() < 0) {
            throw new ValidationException("Stok tidak boleh negatif!");
        }
    }
}

