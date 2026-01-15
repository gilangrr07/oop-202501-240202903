package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import com.upb.agripos.dao.Product;
import com.upb.agripos.model.ProductDAO;
import com.upb.agripos.model.ProductDAOImpl;

public class MainDAOTest {

    public static void main(String[] args) {

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/agripos",
                "postgres",
                "071005"
            );

            System.out.println("Database connected!");

            ProductDAO dao = new ProductDAOImpl(conn);

            // CREATE
            System.out.println("Inserting product...");
            dao.insert(new Product("P01", "Pupuk Organik", 25000, 10));

            // UPDATE
            System.out.println("Updating product...");
            dao.update(new Product("P01", "Pupuk Organik Premium", 100000, 20));

            // READ (BY CODE)
            Product p = dao.findByCode("P01");
            System.out.println(
                "Found: " + p.getName() + " | Price: " + p.getPrice()
            );

            // READ (ALL)
            System.out.println("All Products:");
            List<Product> products = dao.findAll();
            for (Product pr : products) {
                System.out.println("- " + pr.getName() + " (" + pr.getStock() + ")");
            }

            // DELETE
            System.out.println("Deleting product...");
            dao.delete("P01");

            conn.close();

            System.out.println("\ncredit by: 240202903 - Mohamad Gilang Rizki Riomdona");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
