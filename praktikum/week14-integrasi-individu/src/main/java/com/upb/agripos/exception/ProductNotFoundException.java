package com.upb.agripos.exception;

/**
 * ProductNotFoundException - Exception ketika produk tidak ditemukan
 * Integrasi: Bab 9 (Exception Handling)
 */
public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}