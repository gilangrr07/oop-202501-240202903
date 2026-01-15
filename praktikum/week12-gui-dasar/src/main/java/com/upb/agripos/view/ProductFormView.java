package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.model.Product;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductFormView extends VBox {

    private ProductController controller = new ProductController();
    private ListView<String> listView = new ListView<>();
    private Label status = new Label();

    public ProductFormView() {

        Label title = new Label("Agri-POS - Form Input Produk");
        title.setStyle("-fx-font-size:18px;-fx-text-fill:green;-fx-font-weight:bold;");

        TextField kode = new TextField();
        TextField nama = new TextField();
        TextField harga = new TextField();
        TextField stok = new TextField();

        kode.setPromptText("Kode Produk");
        nama.setPromptText("Masukkan nama produk");
        harga.setPromptText("Masukkan harga");
        stok.setPromptText("Masukkan stok");

        Button tambah = new Button("Tambah Produk");
        tambah.setStyle("-fx-background-color:#4CAF50;-fx-text-fill:white;");
        Button refresh = new Button("Refresh");
        refresh.setStyle("-fx-background-color:#2196F3;-fx-text-fill:white;");

        tambah.setOnAction(e -> {
            Product p = new Product(
                kode.getText(),
                nama.getText(),
                Double.parseDouble(harga.getText()),
                Integer.parseInt(stok.getText())
            );

            controller.tambahProduk(p);
            status.setText("Produk berhasil ditambahkan!");
            loadData();
        });

        refresh.setOnAction(e -> loadData());

        this.setPadding(new Insets(15));
        this.setSpacing(10);
        this.getChildren().addAll(
            title,
            kode, nama, harga, stok,
            new HBox(10, tambah, refresh),
            new Label("Daftar Produk:"),
            listView,
            status
        );

        loadData(); // tampilkan data SQL week 11
    }

    private void loadData() {
    listView.getItems().clear();
    
    // Change 'Object' to 'Product' so Java knows what methods are available
    for (Product p : controller.loadProduk()) { 
        listView.getItems().add(
            p.getCode() + " - " + p.getName() +
            " | Rp " + (int)p.getPrice() +
            " | Stok: " + p.getStock()
        );
        }
    }
}
