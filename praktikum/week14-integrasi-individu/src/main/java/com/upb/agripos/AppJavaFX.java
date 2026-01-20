package com.upb.agripos;

import com.upb.agripos.view.PosView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * AppJavaFX - Main Application Class
 * Integrasi: Bab 1 (Identitas), Bab 12-13 (JavaFX)
 */
public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        // Bab 1: Identitas Mahasiswa (ditampilkan di console)
        printIdentity();
        
        System.out.println("Week 14 - Integrasi Individu (Agri-POS System)");
        System.out.println("=".repeat(60));
        
        // Setup JavaFX
        stage.setTitle("🌾 Agri-POS - Point of Sale System");
        
        PosView posView = new PosView();
        Scene scene = new Scene(posView, 1000, 600);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        System.out.println("Application started successfully!");
    }

    @Override
    public void stop() {
        System.out.println("\nApplication closed. Thank you!");
        printIdentity();
    }

    /**
     * Method untuk menampilkan identitas mahasiswa
     * Integrasi Bab 1: Identitas Praktikum
     */
    private static void printIdentity() {
        System.out.println("=".repeat(60));
        System.out.println("Hello, I am Mohamad Gilang Rizki Riomdona-240202903");
        System.out.println("=".repeat(60));
    }

    public static void main(String[] args) {
        launch(args);
    }
}