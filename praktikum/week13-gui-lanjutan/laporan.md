# Laporan Praktikum Minggu 13
Topik: [GUI Lanjutan JavaFX (TableView dan Lambda Expression)]

## Identitas
- Nama  : [Mohamad Gilang Rizki Riomdona]
- NIM   : [240202903]
- Kelas : [3 IKRB]

---

## Tujuan
(Setelah mengikuti praktikum ini, mahasiswa mampu:
1. Menampilkan data menggunakan TableView JavaFX.
2. Mengintegrasikan koleksi objek dengan GUI.
3. Menggunakan lambda expression untuk event handling.
4. Menghubungkan GUI dengan DAO secara penuh.
5. Membangun antarmuka GUI Agri-POS yang lebih interaktif.)

---

## Kode Program
( 

```java
// Product.java
package com.upb.agripos.controller;

import javafx.beans.property.*;

public class Product {

    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final IntegerProperty stock = new SimpleIntegerProperty();

    public Product(String code, String name, double price, int stock) {
        this.code.set(code);
        this.name.set(name);
        this.price.set(price);
        this.stock.set(stock);
    }

    // ===== STANDARD GETTER (WAJIB UNTUK DAO & LOGIC) =====
    public String getCode() {
        return code.get();
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }

    public int getStock() {
        return stock.get();
    }

    // ===== STANDARD SETTER =====
    public void setCode(String code) {
        this.code.set(code);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    // ===== PROPERTY GETTER (UNTUK TABLEVIEW) =====
    public StringProperty codeProperty() {
        return code;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty stockProperty() {
        return stock;
    }
}

//DatabaseConnection.java
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

//ProductDAO.java
package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.upb.agripos.model.Product;

public class ProductDAO {

    private final Connection conn;

    public ProductDAO() {
        conn = DatabaseConnection.getInstance().getConnection();
    }

    // ========================
    // INSERT (INI YANG TADI GAGAL)
    // ========================
    public void insert(Product p) {
        String sql = "INSERT INTO products (code, name, price, stock) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());

            int rows = ps.executeUpdate();
            System.out.println("INSERT ROWS: " + rows);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========================
    // DELETE
    // ========================
    public void delete(String code) {
        String sql = "DELETE FROM products WHERE code = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ========================
    // FIND ALL
    // ========================
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY code";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}

//Product.java
package com.upb.agripos.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final IntegerProperty stock = new SimpleIntegerProperty();

    public Product(String code, String name, double price, int stock) {
        this.code.set(code);
        this.name.set(name);
        this.price.set(price);
        this.stock.set(stock);
    }

    // ===== GETTER BIASA (DAO & SERVICE) =====
    public String getCode() { return code.get(); }
    public String getName() { return name.get(); }
    public double getPrice() { return price.get(); }
    public int getStock() { return stock.get(); }

    // ===== PROPERTY (TABLEVIEW) =====
    public StringProperty codeProperty() { return code; }
    public StringProperty nameProperty() { return name; }
    public DoubleProperty priceProperty() { return price; }
    public IntegerProperty stockProperty() { return stock; }
}

//ProductService.java
package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

public class ProductService {

    private final ProductDAO dao = new ProductDAO();


    public List<Product> findAll() {
        return dao.findAll();
    }

    // ======================
    // CREATE
    // ======================
    public void add(Product product) {
        dao.insert(product);   // 🔥 BUKAN save()
    }

    // ======================
    // DELETE
    // ======================
    public void delete(String code) {
        dao.delete(code);
    }
}

//ProductTableView.java
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


//AppJavaFX.java
package com.upb.agripos;

import com.upb.agripos.view.ProductTableView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("AgriPOS - Week 13");
        stage.setScene(new Scene(new ProductTableView(), 900, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

```
)
---

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](screenshots/OutputGuiLanjutan.png)

![Screenshot hasil](screenshots/OutputSaatMenghapus.png)
)
---

## Analisis
(
- Jelaskan bagaimana kode berjalan.  \
   Program ini merupakan aplikasi JavaFX berbasis GUI yang digunakan untuk mengelola data produk dengan menerapkan konsep pemisahan tanggung jawab antara tampilan, logika aplikasi, dan akses data. Ketika aplikasi dijalankan, kelas ProductTableView akan membangun antarmuka pengguna yang terdiri dari TableView untuk menampilkan data produk, beberapa TextField untuk input data, serta tombol Tambah dan Hapus. Setiap kolom pada TableView dihubungkan langsung dengan properti pada objek Product, sehingga data yang ditampilkan selalu sinkron dengan objek yang ada di dalam program.

   Pada saat aplikasi pertama kali ditampilkan, method loadData() dipanggil untuk mengambil seluruh data produk dari database. Data ini diperoleh melalui ProductDAO yang berperan sebagai penghubung antara aplikasi dan database menggunakan JDBC. Hasil pengambilan data kemudian dimasukkan ke dalam ObservableList, sehingga TableView dapat menampilkan data tersebut secara otomatis dan akan memperbarui tampilan ketika terjadi perubahan data.

   Ketika pengguna menekan tombol Tambah, aplikasi akan membaca nilai yang dimasukkan pada setiap TextField dan membuat sebuah objek Product baru. Objek ini kemudian disimpan ke dalam database melalui method insert() pada ProductDAO. Setelah proses penyimpanan berhasil, tabel akan diperbarui kembali dengan memanggil loadData() agar data terbaru langsung terlihat pada antarmuka, serta form input dikosongkan untuk memudahkan pengisian data berikutnya.

   Sementara itu, proses penghapusan data dilakukan dengan memilih salah satu baris pada TableView lalu menekan tombol Hapus. Sistem akan menampilkan dialog konfirmasi sebagai pengaman agar pengguna tidak menghapus data secara tidak sengaja. Jika pengguna menyetujui penghapusan, data produk akan dihapus dari database berdasarkan kode produk yang dipilih, kemudian tabel diperbarui kembali sehingga perubahan data langsung tercermin pada tampilan. Dengan alur ini, aplikasi mampu menampilkan, menambah, dan menghapus data produk secara terintegrasi antara GUI dan database.

- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya.  
   Pendekatan pada praktikum minggu ini berbeda dari minggu sebelumnya karena fokus utama telah bergeser dari GUI sederhana ke GUI yang lebih interaktif dan terintegrasi penuh dengan database. Pada minggu sebelumnya (Bab 12), tampilan data masih bersifat sederhana, umumnya menggunakan komponen seperti ListView atau TextArea, dan interaksi pengguna hanya terbatas pada penambahan data serta penampilan hasil input secara langsung. Struktur GUI masih menekankan pengenalan konsep event-driven programming dan integrasi awal dengan ProductService dan ProductDAO.

   Pada minggu ini (Bab 13), pendekatan pengembangan ditingkatkan dengan penggunaan TableView sebagai komponen utama untuk menampilkan data. TableView memungkinkan data ditampilkan dalam bentuk tabel yang terstruktur per kolom dan terikat langsung dengan properti objek Product, sehingga lebih rapi, informatif, dan mendekati aplikasi nyata. Selain itu, data yang ditampilkan tidak lagi hanya berasal dari input sementara, tetapi selalu diambil langsung dari database melalui DAO, sehingga mencerminkan kondisi data yang sebenarnya.

   Perbedaan lainnya adalah penggunaan lambda expression secara konsisten pada event handler, yang membuat kode lebih ringkas, mudah dibaca, dan sesuai dengan konsep Java modern. Pada minggu ini juga ditambahkan fitur lanjutan seperti penghapusan data dengan konfirmasi, serta mekanisme pemuatan ulang data (loadData()) untuk menjaga sinkronisasi antara database dan tampilan. Dengan demikian, pendekatan minggu ini lebih menekankan pada integrasi penuh backend–frontend, kematangan arsitektur MVC, serta peningkatan kualitas antarmuka dan pengalaman pengguna dibandingkan minggu sebelumnya.

- Kendala yang dihadapi dan cara mengatasinya.  
   Selama pengerjaan praktikum ini, terdapat beberapa kendala yang dihadapi baik dari sisi teknis maupun integrasi antar komponen. Salah satu kendala utama adalah data yang berhasil disimpan ke database tetapi tidak langsung muncul pada TableView. Masalah ini terjadi karena data pada tabel belum dimuat ulang setelah proses insert atau delete. Kendala ini diatasi dengan memastikan method loadData() selalu dipanggil setelah operasi tambah maupun hapus data, serta menggunakan ObservableList agar perubahan data otomatis tercermin pada tampilan.

   Kendala berikutnya adalah kesalahan pemanggilan method dan inkonsistensi struktur kelas, seperti method save(), delete(), atau getCode() yang tidak ditemukan. Hal ini disebabkan oleh perbedaan nama method antara kelas Product, ProductDAO, dan ProductService. Cara mengatasinya adalah dengan menyeragamkan kembali nama method sesuai dengan desain pada pertemuan sebelumnya (Bab 6 dan Bab 11), serta memastikan bahwa View hanya berkomunikasi dengan Service atau DAO yang benar tanpa membuat logika baru.

   Selain itu, masalah konfigurasi lingkungan juga menjadi kendala, khususnya terkait Maven dan JAVA_HOME yang menyebabkan proyek tidak dapat dijalankan. Permasalahan ini diselesaikan dengan memperbaiki pengaturan JAVA_HOME, memastikan versi Java yang digunakan sesuai (Java 17), serta menjalankan perintah Maven dari direktori yang benar dan memiliki file pom.xml. Terdapat pula kendala encoding file sumber yang menyebabkan error karakter tidak terbaca, yang diatasi dengan menyimpan file menggunakan encoding UTF-8.

   Kendala lain yang muncul adalah kesalahan tampilan GUI, seperti tombol yang kurang jelas atau ikon yang tidak sesuai. Hal ini diatasi dengan melakukan penyesuaian langsung pada komponen JavaFX tanpa menggunakan CSS eksternal, sehingga tampilan tetap sederhana namun lebih rapi dan konsisten. Secara keseluruhan, kendala-kendala tersebut dapat diatasi dengan debugging bertahap, pengecekan log error, serta menjaga konsistensi arsitektur MVC dan integrasi antara GUI, service, DAO, dan database.
)
---

## Kesimpulan
(Berdasarkan praktikum minggu ini, dapat disimpulkan bahwa penggunaan JavaFX dengan komponen TableView memungkinkan pembuatan antarmuka aplikasi yang lebih interaktif, terstruktur, dan mendekati aplikasi nyata. Integrasi antara GUI, service, dan DAO yang terhubung langsung ke database membuat pengelolaan data produk menjadi lebih konsisten dan terjaga, karena setiap perubahan data pada antarmuka langsung tersimpan dan disinkronkan dengan database.

Selain itu, penerapan konsep event-driven programming dan lambda expression membuat penanganan aksi pengguna menjadi lebih ringkas dan mudah dipahami. Dengan menjaga pemisahan tanggung jawab sesuai arsitektur MVC, kode program menjadi lebih rapi, mudah dipelihara, dan siap dikembangkan lebih lanjut pada fitur lanjutan. Praktikum ini juga memperkuat pemahaman mahasiswa tentang pentingnya konsistensi desain, integrasi frontend–backend, serta penerapan konsep OOP dalam membangun aplikasi desktop berbasis Java.)

---
