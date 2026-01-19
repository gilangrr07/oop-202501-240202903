package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    //SESUAI DATABASE WEEK 11
    private static final String URL =
            "jdbc:postgresql://localhost:5432/agripos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "071005";

    // =============================
    // PRIVATE CONSTRUCTOR (SINGLETON)
    // =============================
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Gagal koneksi database", e);
        }
    }

    // =============================
    // SINGLETON ACCESSOR
    // =============================
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // =============================
    // GET CONNECTION
    // =============================
    public Connection getConnection() {
        return connection;
    }
}
