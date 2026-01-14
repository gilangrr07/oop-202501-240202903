package com.upb.agripos.config;

public class DatabaseConnection {
    private static DatabaseConnection instance;

    // constructor private
    private DatabaseConnection() {
        System.out.println("Database Connected");
    }

    // method global access
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}