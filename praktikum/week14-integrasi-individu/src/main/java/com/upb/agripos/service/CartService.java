package com.upb.agripos.service;

import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.exception.InsufficientStockException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Cart;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.Transaction;

/**
 * CartService - Business Logic untuk Keranjang Belanja
 * Integrasi: Bab 7 (Collections), Bab 9 (Exception)
 */
public class CartService {
    
    private final Cart cart;
    private final ProductService productService;

    public CartService() {
        this.cart = new Cart();
        this.productService = new ProductService();
    }

    public CartService(ProductService productService) {
        this.cart = new Cart();
        this.productService = productService;
    }

    public void addToCart(Product product, int quantity) throws ValidationException, InsufficientStockException {
        if (product == null) {
            throw new ValidationException("Produk tidak boleh null!");
        }
        
        if (quantity <= 0) {
            throw new ValidationException("Quantity harus lebih dari 0!");
        }
        
        if (product.getStock() < quantity) {
            throw new InsufficientStockException(
                "Stok tidak cukup! Tersedia: " + product.getStock() + ", Diminta: " + quantity
            );
        }
        
        cart.addItem(product, quantity);
    }

    public void removeFromCart(String productCode) throws ValidationException {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new ValidationException("Kode produk tidak boleh kosong!");
        }
        
        cart.removeItem(productCode);
    }

    public void updateQuantity(String productCode, int newQuantity) throws ValidationException {
        if (newQuantity <= 0) {
            throw new ValidationException("Quantity harus lebih dari 0!");
        }
        
        cart.updateQuantity(productCode, newQuantity);
    }

    public List<CartItem> getCartItems() {
        return cart.getItems();
    }

    public double getTotal() {
        return cart.getTotal();
    }

    public void clearCart() {
        cart.clear();
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }

    public int getItemCount() {
        return cart.getItemCount();
    }

    public Transaction checkout(String cashierName) throws InsufficientStockException, DatabaseException, ValidationException {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Keranjang kosong!");
        }
        
        // Validasi stok untuk semua item
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getStock() < item.getQuantity()) {
                throw new InsufficientStockException(
                    "Stok tidak cukup untuk " + item.getProduct().getName()
                );
            }
        }
        
        // Buat transaksi
        String invoiceNo = Transaction.generateInvoiceNo();
        List<CartItem> soldItems = new ArrayList<>(cart.getItems());
        double total = cart.getTotal();
        
        Transaction transaction = new Transaction(invoiceNo, soldItems, total, cashierName);
        
        // Kurangi stok di memory DAN database
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            product.reduceStock(item.getQuantity());
            
            // 🔥 UPDATE KE DATABASE - INI YANG KURANG!
            try {
                productService.updateStock(product);
            } catch (Exception e) {
                // Rollback jika gagal
                product.addStock(item.getQuantity());
                throw new DatabaseException("Gagal update stok ke database: " + e.getMessage());
            }
        }
        
        cart.clear();
        
        return transaction;
    }
}