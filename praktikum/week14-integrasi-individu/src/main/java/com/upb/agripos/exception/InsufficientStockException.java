package com.upb.agripos.exception;

/**
 * InsufficientStockException - Exception ketika stok tidak cukup
 * Integrasi: Bab 9 (Exception Handling)
 */
public class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}
