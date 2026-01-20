package com.upb.agripos.view;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.Transaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * PosView - Main GUI View menggunakan JavaFX
 * Integrasi: Bab 12 & 13 (JavaFX), Bab 6 (MVC - View)
 */
public class PosView extends BorderPane {

    private final PosController controller;
    
    // Product Table
    private final TableView<Product> productTable = new TableView<>();
    private final ObservableList<Product> productData = FXCollections.observableArrayList();
    
    // Cart Area
    private final ListView<String> cartListView = new ListView<>();
    private final Label totalLabel = new Label("Total: Rp 0");
    
    // Form Fields
    private final TextField txtCode = new TextField();
    private final TextField txtName = new TextField();
    private final TextField txtPrice = new TextField();
    private final TextField txtStock = new TextField();
    private final TextField txtQuantity = new TextField();

    public PosView() {
        this.controller = new PosController();
        initializeUI();
        loadProducts();
    }

    private void initializeUI() {
        setPadding(new Insets(15));
        
        // Header
        Label header = new Label("🌾 Agri-POS - Point of Sale System");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setTextFill(Color.GREEN);
        setTop(header);
        BorderPane.setAlignment(header, Pos.CENTER);
        BorderPane.setMargin(header, new Insets(0, 0, 15, 0));
        
        // Left: Product Management
        VBox leftPanel = createProductPanel();
        
        // Right: Shopping Cart
        VBox rightPanel = createCartPanel();
        
        // Main Layout
        HBox mainContent = new HBox(15, leftPanel, rightPanel);
        setCenter(mainContent);
    }

    private VBox createProductPanel() {
        VBox panel = new VBox(10);
        panel.setPrefWidth(600);
        
        // Title
        Label title = new Label("📦 Manajemen Produk");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // Form
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        
        txtCode.setPromptText("Kode Produk");
        txtName.setPromptText("Nama Produk");
        txtPrice.setPromptText("Harga");
        txtStock.setPromptText("Stok");
        
        form.add(new Label("Kode:"), 0, 0);
        form.add(txtCode, 1, 0);
        form.add(new Label("Nama:"), 0, 1);
        form.add(txtName, 1, 1);
        form.add(new Label("Harga:"), 0, 2);
        form.add(txtPrice, 1, 2);
        form.add(new Label("Stok:"), 0, 3);
        form.add(txtStock, 1, 3);
        
        // Buttons
        Button btnAdd = new Button("➕ Tambah Produk");
        Button btnDelete = new Button("🗑️ Hapus Produk");
        Button btnRefresh = new Button("🔄 Refresh");
        
        btnAdd.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnRefresh.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        
        btnAdd.setOnAction(e -> handleAddProduct());
        btnDelete.setOnAction(e -> handleDeleteProduct());
        btnRefresh.setOnAction(e -> loadProducts());
        
        HBox buttonBox = new HBox(10, btnAdd, btnDelete, btnRefresh);
        
        // Table
        setupProductTable();
        
        panel.getChildren().addAll(title, form, buttonBox, productTable);
        return panel;
    }

    private void setupProductTable() {
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCode.setPrefWidth(80);
        
        TableColumn<Product, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(200);
        
        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setPrefWidth(100);
        
        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStock.setPrefWidth(80);
        
        productTable.getColumns().addAll(colCode, colName, colPrice, colStock);
        productTable.setItems(productData);
        productTable.setPrefHeight(300);
    }

    private VBox createCartPanel() {
        VBox panel = new VBox(10);
        panel.setPrefWidth(350);
        
        // Title
        Label title = new Label("🛒 Keranjang Belanja");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // Quantity Input
        HBox qtyBox = new HBox(10);
        txtQuantity.setPromptText("Qty");
        txtQuantity.setPrefWidth(60);
        Button btnAddToCart = new Button("➕ Tambah ke Keranjang");
        btnAddToCart.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAddToCart.setOnAction(e -> handleAddToCart());
        qtyBox.getChildren().addAll(new Label("Jumlah:"), txtQuantity, btnAddToCart);
        
        // Cart List
        cartListView.setPrefHeight(200);
        
        // Total
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        totalLabel.setTextFill(Color.DARKGREEN);
        
        // Buttons
        Button btnCheckout = new Button("💳 Checkout");
        Button btnClearCart = new Button("🗑️ Kosongkan Keranjang");
        
        btnCheckout.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnClearCart.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        
        btnCheckout.setOnAction(e -> handleCheckout());
        btnClearCart.setOnAction(e -> handleClearCart());
        
        HBox cartButtons = new HBox(10, btnCheckout, btnClearCart);
        
        panel.getChildren().addAll(title, qtyBox, new Label("Item di Keranjang:"), 
                                    cartListView, totalLabel, cartButtons);
        return panel;
    }

    // ===== EVENT HANDLERS =====
    
    private void handleAddProduct() {
        try {
            String code = txtCode.getText();
            String name = txtName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());
            
            controller.addProduct(code, name, price, stock);
            
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Produk berhasil ditambahkan!");
            clearProductForm();
            loadProducts();
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Harga dan stok harus berupa angka!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void handleDeleteProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih produk yang akan dihapus!");
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Hapus");
        confirm.setHeaderText("Yakin ingin menghapus produk?");
        confirm.setContentText(selected.getName());
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    controller.deleteProduct(selected.getCode());
                    showAlert(Alert.AlertType.INFORMATION, "Sukses", "Produk berhasil dihapus!");
                    loadProducts();
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                }
            }
        });
    }

    private void handleAddToCart() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Pilih produk terlebih dahulu!");
            return;
        }
        
        try {
            int quantity = Integer.parseInt(txtQuantity.getText());
            controller.addToCart(selected, quantity);
            
            updateCartView();
            txtQuantity.clear();
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Jumlah harus berupa angka!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void handleCheckout() {
        if (controller.isCartEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Keranjang masih kosong!");
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Checkout");
        confirm.setHeaderText("Lanjutkan pembayaran?");
        confirm.setContentText("Total: Rp " + String.format("%,.2f", controller.getCartTotal()));
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Checkout dan dapatkan transaction
                    String cashierName = "Mohamad Gilang Rizki Riomdona";
                    Transaction transaction = controller.checkout(cashierName);
                    
                    // Tampilkan struk di console
                    String receipt = transaction.generateReceipt();
                    System.out.println(receipt);
                    
                    // Tampilkan struk di dialog
                    showReceiptDialog(receipt);
                    
                    // Update UI
                    updateCartView();
                    loadProducts(); // Refresh stok
                    
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                }
            }
        });
    }

    private void showReceiptDialog(String receipt) {
        Alert receiptAlert = new Alert(Alert.AlertType.INFORMATION);
        receiptAlert.setTitle("✅ Checkout Berhasil");
        receiptAlert.setHeaderText("Terima kasih atas pembelian Anda!");
        
        // Gunakan TextArea untuk struk dengan font monospace
        TextArea receiptArea = new TextArea(receipt);
        receiptArea.setEditable(false);
        receiptArea.setWrapText(false);
        receiptArea.setFont(Font.font("Courier New", 12));
        receiptArea.setPrefSize(600, 500);
        
        receiptAlert.getDialogPane().setContent(receiptArea);
        receiptAlert.getDialogPane().setPrefSize(650, 550);
        
        receiptAlert.showAndWait();
    }

    private void handleClearCart() {
        controller.clearCart();
        updateCartView();
    }

    private void updateCartView() {
        cartListView.getItems().clear();
        
        for (CartItem item : controller.getCartItems()) {
            cartListView.getItems().add(item.toString());
        }
        
        totalLabel.setText(String.format("Total: Rp %.2f", controller.getCartTotal()));
    }

    private void loadProducts() {
        try {
            productData.clear();
            productData.addAll(controller.loadProducts());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Gagal memuat data: " + e.getMessage());
        }
    }

    private void clearProductForm() {
        txtCode.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}