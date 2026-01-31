<<<<<<< HEAD
package com.upb.agripos.model;

public class CartItem {
    private Product product;
    private Integer quantity;

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Double getSubtotal() {
        return product.getHarga() * quantity;
    }
}
=======
package com.upb.agripos.model;

public class CartItem {
    private Product product;
    private Integer quantity;

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Double getSubtotal() {
        return product.getHarga() * quantity;
    }
}
>>>>>>> 4e34a79db5d9380e3d045243de31f412c2766f3f
