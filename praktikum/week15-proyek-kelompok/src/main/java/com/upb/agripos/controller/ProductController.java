<<<<<<< HEAD
package com.upb.agripos.controller;

import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import java.util.List;

public class ProductController {
    private ProductService productService;
    
    public ProductController() {
        this.productService = new ProductService();
    }
    
    public void addProduct(String kode, String nama, String kategori, 
                          double harga, int stok) throws Exception {
        Product product = new Product(kode, nama, kategori, harga, stok);
        productService.addProduct(product);
    }
    
    public void updateProduct(String kode, String nama, String kategori, 
                             double harga, int stok) throws Exception {
        Product product = new Product(kode, nama, kategori, harga, stok);
        productService.updateProduct(product);
    }
    
    public void deleteProduct(String kode) throws Exception {
        productService.deleteProduct(kode);
    }
    
    public Product getProductByKode(String kode) throws Exception {
        return productService.getProductByKode(kode);
    }
    
    public List<Product> getAllProducts() throws Exception {
        return productService.getAllProducts();
    }
    
    public void refreshProducts() throws Exception {
        getAllProducts();
    }
}
=======
package com.upb.agripos.controller;

import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import java.util.List;

public class ProductController {
    private ProductService productService;
    
    public ProductController() {
        this.productService = new ProductService();
    }
    
    public void addProduct(String kode, String nama, String kategori, 
                          double harga, int stok) throws Exception {
        Product product = new Product(kode, nama, kategori, harga, stok);
        productService.addProduct(product);
    }
    
    public void updateProduct(String kode, String nama, String kategori, 
                             double harga, int stok) throws Exception {
        Product product = new Product(kode, nama, kategori, harga, stok);
        productService.updateProduct(product);
    }
    
    public void deleteProduct(String kode) throws Exception {
        productService.deleteProduct(kode);
    }
    
    public Product getProductByKode(String kode) throws Exception {
        return productService.getProductByKode(kode);
    }
    
    public List<Product> getAllProducts() throws Exception {
        return productService.getAllProducts();
    }
    
    public void refreshProducts() throws Exception {
        getAllProducts();
    }
}
>>>>>>> 4e34a79db5d9380e3d045243de31f412c2766f3f
