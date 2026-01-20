package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.model.Product;
import com.upb.agripos.util.DatabaseConnection;

/**
 * JdbcProductDAO - Implementasi ProductDAO menggunakan JDBC
 * Integrasi: Bab 11 (JDBC + DAO Pattern)
 */
public class JdbcProductDAO implements ProductDAO {
    
    private final Connection connection;

    public JdbcProductDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void insert(Product product) throws DatabaseException {
        String sql = "INSERT INTO products (code, name, price, stock) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getCode());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DatabaseException("Failed to insert product");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error during insert: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Product product) throws DatabaseException {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ? WHERE code = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setString(4, product.getCode());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error during update: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String code) throws DatabaseException {
        String sql = "DELETE FROM products WHERE code = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error during delete: " + e.getMessage(), e);
        }
    }

    @Override
    public Product findByCode(String code) throws DatabaseException {
        String sql = "SELECT * FROM products WHERE code = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error during findByCode: " + e.getMessage(), e);
        }
        
        return null;
    }

    @Override
    public List<Product> findAll() throws DatabaseException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY code";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error during findAll: " + e.getMessage(), e);
        }
        
        return products;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
            rs.getString("code"),
            rs.getString("name"),
            rs.getDouble("price"),
            rs.getInt("stock")
        );
    }
}