package main.java.com.upb.agripos;

public class MainExceptionDemo {

    public static void main(String[] args) {

        System.out.println("Hello, I am Mohamad Gilang Rizki Riomdona-240202903 (Week9)");

        ShoppingCart cart = new ShoppingCart();
        Product pupuk = new Product(
                "P01",
                "Pupuk Organik",
                25000,
                3
        );

        // 1. Uji qty tidak valid
        try {
            cart.addProduct(pupuk, -1);
        } catch (InvalidQuantityException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // 2. Uji hapus produk yang belum ada
        try {
            cart.removeProduct(pupuk);
        } catch (ProductNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // 3. Uji stok tidak cukup
        try {
            cart.addProduct(pupuk, 5);
            cart.checkout();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}