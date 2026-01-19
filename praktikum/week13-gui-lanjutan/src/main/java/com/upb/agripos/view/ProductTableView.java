package com.upb.agripos.view;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ProductTableView extends BorderPane {

    private final TableView<Product> table = new TableView<>();
    private final ObservableList<Product> products =
            FXCollections.observableArrayList();

    private final TextField txtCode = new TextField();
    private final TextField txtName = new TextField();
    private final TextField txtPrice = new TextField();
    private final TextField txtStock = new TextField();

    private final Button btnAdd = new Button("Tambah");
    private final Button btnDelete = new Button("Hapus");

    private final ProductDAO dao = new ProductDAO();

    public ProductTableView() {

        // ===== TABLE =====
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(d -> d.getValue().codeProperty());
        colCode.setPrefWidth(100);

        TableColumn<Product, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(d -> d.getValue().nameProperty());
        colName.setPrefWidth(200);

        TableColumn<Product, Number> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(d -> d.getValue().priceProperty());
        colPrice.setPrefWidth(150);

        TableColumn<Product, Number> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(d -> d.getValue().stockProperty());
        colStock.setPrefWidth(100);

        table.getColumns().addAll(colCode, colName, colPrice, colStock);
        table.setItems(products);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ===== FORM =====
        txtCode.setPromptText("Kode");
        txtName.setPromptText("Nama Produk");
        txtPrice.setPromptText("Harga");
        txtStock.setPromptText("Stok");

        txtCode.setPrefWidth(90);
        txtName.setPrefWidth(180);
        txtPrice.setPrefWidth(120);
        txtStock.setPrefWidth(90);

        // Styling tombol (tanpa CSS)
        btnAdd.setStyle(
                "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        btnDelete.setStyle(
                "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        btnAdd.setOnAction(e -> addProduct());
        btnDelete.setOnAction(e -> deleteProduct());

        HBox form = new HBox(10,
                txtCode, txtName, txtPrice, txtStock, btnAdd, btnDelete);
        form.setPadding(new Insets(10));

        setTop(form);
        setCenter(table);

        // ===== LOAD DATA =====
        loadData();
    }

    private void loadData() {
        products.clear();
        products.addAll(dao.findAll());
    }

    private void addProduct() {
        try {
            Product p = new Product(
                    txtCode.getText(),
                    txtName.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText())
            );

            dao.insert(p);
            loadData();
            clearForm();

            showAlert("Sukses", "Produk berhasil ditambahkan!");

        } catch (Exception e) {
            showAlert("Error", "Pastikan semua data diisi dengan benar!");
        }
    }

    // ✅ KONFIRMASI HAPUS
    private void deleteProduct() {
        Product selected = table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Info", "Pilih produk yang akan dihapus!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Hapus");
        confirm.setHeaderText("Yakin ingin menghapus produk?");
        confirm.setContentText(
                "Kode : " + selected.getCode() + "\n" +
                "Nama : " + selected.getName()
        );

        ButtonType btnYes = new ButtonType("Ya");
        ButtonType btnNo = new ButtonType("Tidak", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirm.getButtonTypes().setAll(btnYes, btnNo);

        confirm.showAndWait().ifPresent(response -> {
            if (response == btnYes) {
                dao.delete(selected.getCode());
                loadData();
            }
        });
    }

    private void clearForm() {
        txtCode.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

