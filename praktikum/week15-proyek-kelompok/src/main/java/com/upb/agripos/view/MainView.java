<<<<<<< HEAD
package com.upb.agripos.view;

import com.upb.agripos.controller.LoginController;
import com.upb.agripos.model.User;
import com.upb.agripos.util.SessionManager;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainView {

    private final LoginController loginController;
    private TabPane tabPane;
    private final User currentUser;

    public MainView() {
        this.loginController = new LoginController();
        this.currentUser = SessionManager.getInstance().getCurrentUser();
    }

    public void show(Stage stage) {
        BorderPane root = new BorderPane();

        // ===== Header =====
        HBox header = createHeader(stage);
        root.setTop(header);

        // ===== Tab Pane =====
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        if (currentUser.isKasir()) {
            Tab cashierTab = new Tab("Transaksi Penjualan");
            cashierTab.setContent(new CashierView());
            tabPane.getTabs().add(cashierTab);

        } else if (currentUser.isAdmin()) {
            Tab productTab = new Tab("Manajemen Produk");
            productTab.setContent(new ProductManagementView());

            Tab reportTab = new Tab("Laporan Penjualan");
            reportTab.setContent(new ReportView());

            tabPane.getTabs().addAll(productTab, reportTab);
        }

        root.setCenter(tabPane);

        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("AGRI-POS - " + currentUser.getRole());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private HBox createHeader(Stage stage) {
        HBox header = new HBox(20);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: #2e7d32;");

        Label titleLabel = new Label("AGRI-POS");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userLabel = new Label(
                currentUser.getFullName() + " (" + currentUser.getRole() + ")");
        userLabel.setFont(Font.font("Arial", 14));
        userLabel.setStyle("-fx-text-fill: white;");

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle(
                "-fx-background-color: #c62828;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;");
        logoutButton.setOnAction(e -> handleLogout(stage));

        header.getChildren().addAll(titleLabel, spacer, userLabel, logoutButton);
        return header;
    }

    private void handleLogout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText("Apakah Anda yakin ingin logout?");
        alert.setContentText("Anda akan kembali ke halaman login.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                loginController.logout();

                LoginView loginView = new LoginView();
                loginView.show(stage);
            }
        });
    }
}
=======
package com.upb.agripos.view;

import com.upb.agripos.controller.LoginController;
import com.upb.agripos.model.User;
import com.upb.agripos.util.SessionManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainView {
    private LoginController loginController;
    private TabPane tabPane;
    private User currentUser;
    
    public MainView() {
        this.loginController = new LoginController();
        this.currentUser = SessionManager.getInstance().getCurrentUser();
    }
    
    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        
        // Header
        HBox header = createHeader(stage);
        root.setTop(header);
        
        // Tab pane
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Add tabs based on role
        if (currentUser.isKasir()) {
            // Kasir only sees transaction tab
            Tab cashierTab = new Tab("Transaksi Penjualan");
            cashierTab.setContent(new CashierView());
            tabPane.getTabs().add(cashierTab);
            
        } else if (currentUser.isAdmin()) {
            // Admin sees all tabs
            Tab productTab = new Tab("Manajemen Produk");
            productTab.setContent(new ProductManagementView());
            
            Tab cashierTab = new Tab("Transaksi Penjualan");
            cashierTab.setContent(new CashierView());
            
            Tab reportTab = new Tab("Laporan Penjualan");
            reportTab.setContent(new ReportView());
            
            tabPane.getTabs().addAll(productTab, cashierTab, reportTab);
        }
        
        root.setCenter(tabPane);
        
        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("AGRI-POS - " + currentUser.getRole());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    
    private HBox createHeader(Stage stage) {
        HBox header = new HBox(20);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: #2e7d32;");
        
        // Title
        Label titleLabel = new Label("AGRI-POS");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: white;");
        
        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // User info
        Label userLabel = new Label(currentUser.getFullName() + " (" + currentUser.getRole() + ")");
        userLabel.setFont(Font.font("Arial", 14));
        userLabel.setStyle("-fx-text-fill: white;");
        
        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; " +
                             "-fx-font-weight: bold; -fx-background-radius: 5;");
        logoutButton.setOnAction(e -> handleLogout(stage));
        
        header.getChildren().addAll(titleLabel, spacer, userLabel, logoutButton);
        return header;
    }
    
    private void handleLogout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Logout");
        alert.setHeaderText("Apakah Anda yakin ingin logout?");
        alert.setContentText("Anda akan kembali ke halaman login.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                loginController.logout();
                
                // Show login view
                LoginView loginView = new LoginView();
                loginView.show(stage);
            }
        });
    }
}
>>>>>>> 4e34a79db5d9380e3d045243de31f412c2766f3f
