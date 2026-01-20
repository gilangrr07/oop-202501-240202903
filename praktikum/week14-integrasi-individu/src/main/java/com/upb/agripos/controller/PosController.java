package com.upb.agripos.controller;

import java.util.List;

import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.exception.InsufficientStockException;
import com.upb.agripos.exception.ProductNotFoundException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;

/**
 * PosController - Controller untuk menghubungkan View dan Service
 * Integrasi: Bab 6 (MVC Pattern, SOLID - DIP), Bab 10 (MVC)
 * 
 * Prinsip DIP: Controller tidak langsung akses DAO, hanya lewat Service
 */
public class PosController {
    
    private final ProductService productService;
    private final CartService cartService;

    public PosController() {
        this.productService = new ProductService();
        this.cartService = new CartService();
    }

    public PosController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    // ===== PRODUCT OPERATIONS =====
    
    public void addProduct(String code, String name, double price, int stock) 
            throws ValidationException, DatabaseException {
        Product product = new Product(code, name, price, stock);
        productService.addProduct(product);
    }

    public void deleteProduct(String code) 
            throws ValidationException, DatabaseException, ProductNotFoundException {
        productService.deleteProduct(code);
    }

    public List<Product> loadProducts() throws DatabaseException {
        return productService.getAllProducts();
    }

    public Product getProduct(String code) throws DatabaseException, ProductNotFoundException {
        return productService.getProductByCode(code);
    }

    // ===== CART OPERATIONS =====
    
    public void addToCart(Product product, int quantity) 
            throws ValidationException, InsufficientStockException {
        cartService.addToCart(product, quantity);
    }

    public void removeFromCart(String productCode) throws ValidationException {
        cartService.removeFromCart(productCode);
    }

    public List<CartItem> getCartItems() {
        return cartService.getCartItems();
    }

    public double getCartTotal() {
        return cartService.getTotal();
    }

    public void clearCart() {
        cartService.clearCart();
    }

    public boolean isCartEmpty() {
        return cartService.isEmpty();
    }

    public int getCartItemCount() {
        return cartService.getItemCount();
    }

    public Transaction checkout(String cashierName) throws InsufficientStockException, DatabaseException, ValidationException {
        return cartService.checkout(cashierName);
    }
}