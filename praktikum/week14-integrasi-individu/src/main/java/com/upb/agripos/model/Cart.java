package com.upb.agripos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cart - Keranjang Belanja
 * Integrasi: Bab 7 (Collections - List & Map)
 */
public class Cart {
    private final Map<String, CartItem> items = new HashMap<>();

    public void addItem(Product product, int quantity) {
        String code = product.getCode();
        
        if (items.containsKey(code)) {
            CartItem existing = items.get(code);
            existing.setQuantity(existing.getQuantity() + quantity);
        } else {
            items.put(code, new CartItem(product, quantity));
        }
    }

    public void removeItem(String productCode) {
        items.remove(productCode);
    }

    public void updateQuantity(String productCode, int newQuantity) {
        if (items.containsKey(productCode)) {
            items.get(productCode).setQuantity(newQuantity);
        }
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public double getTotal() {
        return items.values().stream()
            .mapToDouble(CartItem::getSubtotal)
            .sum();
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getItemCount() {
        return items.size();
    }
}