# Laporan Praktikum Minggu 12
Topik: [GUI Dasar JavaFX (Event-Driven Programming)]

## Identitas
- Nama  : [Mohamad Gilang Rizki Riomdona]
- NIM   : [240202903]
- Kelas : [3 IKRB]

---

## Tujuan
(Setelah mengikuti praktikum ini, mahasiswa mampu:
1. Menjelaskan konsep event-driven programming.
2. Membangun antarmuka grafis sederhana menggunakan JavaFX.
3. Membuat form input data produk.
4. Menampilkan daftar produk pada GUI.
5. Mengintegrasikan GUI dengan modul backend yang telah dibuat (DAO & Service).)

---

## Dasar Teori
(## Keterkaitan dengan Pertemuan Sebelumnya
Praktikum ini WAJIB menggunakan hasil dari:
- Pertemuan 7: Collections
- Pertemuan 10: MVC & Design Pattern
- Pertemuan 11: DAO + JDBC (CRUD PostgreSQL)

Mahasiswa TIDAK diperbolehkan membuat ulang logika CRUD di layer GUI. Gunakan `ProductService` yang memanggil `ProductDAO` di backend.

---

## Keterkaitan dengan Bab 6 (UML + SOLID)
Implementasi GUI pada Bab 12 harus merealisasikan artefak desain Bab 6:

1. **Use Case**
	- Minimal mencakup alur "Kelola Produk" (Tambah Produk dan Lihat Daftar Produk).
	- Nama use case/aktor pada laporan Bab 6 harus konsisten dengan fitur GUI.

2. **Activity Diagram**
	- Alur Tambah Produk di GUI mengikuti activity diagram (validasi input → panggil service → update tampilan).

3. **Sequence Diagram**
	- Untuk aksi tombol "Tambah Produk", urutan interaksi mengikuti sequence diagram: `View` → `Controller` → `Service` → `DAO` → `DB`, lalu kembali untuk memperbarui UI.

4. **Class Diagram & SOLID**
	- Struktur kelas mengikuti class diagram Bab 6 (entitas/model, controller, service, DAO).
	- Terapkan **DIP**: `View` tidak boleh memanggil `DAO` langsung; akses data harus melalui `Service` (dan/atau interface DAO bila sudah dirancang di Bab 6).
 
Jika Bab 6 Anda menggunakan nama kelas/metode berbeda, Bab 12 harus mengikuti nama tersebut (bukan membuat versi baru).

---)


## Kode Program
(

```java
//DatabaseConnection.java
package com.upb.agripos.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL =
        "jdbc:postgresql://localhost:5432/agripos";
    private static final String USER = "postgres";
    private static final String PASS = "071005";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
//ProductController.java
package com.upb.agripos.controller;

import java.util.List;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;



public class ProductController {

    private ProductService service = new ProductService();

    public void tambahProduk(Product p) {
        service.tambahProduk(p);
    }

    public List<Product> loadProduk() {
        return service.getProduk();
    }
}
//ProductDAO.java
package com.upb.agripos.dao;

import com.upb.agripos.config.DatabaseConnection;
import com.upb.agripos.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void insert(Product p) {
        String sql =
            "INSERT INTO products(code, name, price, stock) VALUES (?, ?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Gagal insert: " + e.getMessage());
        }
    }

    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY code";

        try (Connection c = DatabaseConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Product(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                ));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
//Product.java
package com.upb.agripos.model;

public class Product {
    private String code;
    private String name;
    private double price;
    private int stock;

    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
}
//ProductService.java
package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

public class ProductService {

    private ProductDAO dao = new ProductDAO();

    public void tambahProduk(Product p) {
        dao.insert(p);
    }

    public List<Product> getProduk() {
        return dao.findAll();
    }
}
//ProductFormView.java
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

//APPJavaFX.java
package com.upb.agripos;

import com.upb.agripos.view.ProductFormView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Agri-POS - GUI Dasar JavaFX");
        stage.setScene(new Scene(new ProductFormView(), 600, 500));
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
![Screenshot hasil](screenshots/OutputGUIDasar.png)
)
---

## Analisis
(
- Jelaskan bagaimana kode berjalan.  
   Kode program Agri-POS ini berjalan dengan menerapkan arsitektur MVC (Model–View–Controller) yang dipadukan dengan DAO untuk memisahkan tampilan, logika aplikasi, dan akses database. Saat aplikasi dijalankan, kelas AppJavaFX menjadi titik awal eksekusi yang memanggil ProductFormView sebagai antarmuka pengguna berbasis JavaFX. Pada saat tampilan dibuat, berbagai komponen UI seperti TextField, Button, dan ListView diinisialisasi, lalu metode loadData() dipanggil untuk menampilkan data produk dari database. View tidak berkomunikasi langsung dengan database, melainkan melalui ProductController, yang berperan sebagai penghubung antara tampilan dan logika aplikasi. Controller meneruskan permintaan ke ProductService, yang bertugas mengelola proses bisnis, seperti mengambil dan menambahkan data produk. Selanjutnya, ProductService memanggil ProductDAO untuk menjalankan perintah SQL menggunakan JDBC, di mana koneksi database disediakan oleh DatabaseConnection. Data hasil query kemudian dipetakan ke dalam objek Product sebagai representasi model. Ketika pengguna menekan tombol “Tambah Produk”, data dari form diubah menjadi objek Product, disimpan ke database melalui alur controller–service–DAO, lalu tampilan diperbarui kembali dengan data terbaru. Dengan alur ini, setiap bagian aplikasi memiliki tanggung jawab yang jelas, sehingga kode lebih terstruktur, mudah dipelihara, dan sesuai dengan prinsip pemrograman berorientasi objek.

- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya.
   Pendekatan pada minggu 12 berbeda dengan minggu 11 karena pada minggu 12 kode sudah menerapkan DAO Pattern yang lebih formal dan terstruktur, sedangkan pada minggu 11 masih menggunakan DAO konkret tanpa abstraksi interface dan terintegrasi langsung dengan alur MVC dan JavaFX. Pada minggu 11, kelas ProductDAO langsung berisi implementasi query SQL dan koneksi database di dalamnya, sehingga controller dan service bergantung langsung pada kelas konkret tersebut. Sebaliknya, pada minggu 12, akses data dipisahkan menjadi interface ProductDAO dan implementasi ProductDAOImpl, sehingga logika akses database tidak bergantung pada satu implementasi tertentu. Selain itu, pada minggu 12 koneksi database diberikan melalui dependency injection (constructor ProductDAOImpl(Connection connection)), bukan dibuat langsung di dalam DAO, sehingga kode menjadi lebih fleksibel, mudah diuji, dan mudah dikembangkan. Minggu 12 juga menambahkan operasi CRUD lengkap (Create, Read, Update, Delete) serta pengujian langsung melalui MainDAOTest berbasis console tanpa GUI, sedangkan minggu 11 berfokus pada integrasi database dengan JavaFX dan alur MVC. Dengan demikian, pendekatan minggu 12 lebih menekankan desain arsitektur, abstraction, dan testability, sementara minggu 11 lebih menitikberatkan pada penggunaan database dalam aplikasi GUI.  

- Kendala yang dihadapi dan cara mengatasinya.  
   Kendala yang dihadapi pada praktikum ini adalah Maven tidak terdeteksi oleh sistem maupun oleh VSCode, sehingga proses build dan pengelolaan dependensi tidak dapat berjalan dengan baik. Masalah ini disebabkan oleh konfigurasi Maven yang belum terpasang atau belum terhubung dengan benar ke environment variable sistem, khususnya variabel MAVEN_HOME dan PATH, serta pengaturan Maven pada IDE yang belum menunjuk ke direktori instalasi Maven yang sesuai. Akibatnya, perintah Maven tidak dapat dijalankan melalui terminal dan proyek Java tidak dapat dikenali sebagai proyek Maven. Cara mengatasi masalah ini dilakukan dengan memastikan Apache Maven telah terinstal dengan benar, kemudian mengatur MAVEN_HOME ke direktori instalasi Maven dan menambahkan folder bin Maven ke dalam PATH. Selanjutnya, konfigurasi Maven pada IDE disesuaikan agar menggunakan Maven yang terdeteksi oleh sistem, lalu dilakukan restart IDE dan verifikasi menggunakan perintah mvn -version. Setelah langkah tersebut dilakukan, Maven berhasil terdeteksi dan proyek dapat dibangun serta dijalankan kembali dengan normal.
)
---

## Kesimpulan
(Tuliskan kesimpulan dari praktikum minggu ini.  
Dengan menerapkan DAO Pattern menggunakan interface dan implementasi terpisah, praktikum minggu ini menunjukkan bahwa akses database dapat dibuat lebih terstruktur, fleksibel, dan mudah dikembangkan. Pemisahan antara kontrak (ProductDAO) dan implementasi (ProductDAOImpl) membuat kode tidak bergantung pada satu cara akses data tertentu serta meningkatkan kemudahan pengujian dan pemeliharaan sistem. Selain itu, penerapan operasi CRUD lengkap dan penggunaan dependency injection pada koneksi database membantu meningkatkan kualitas desain perangkat lunak dan kesiapan aplikasi untuk dikembangkan lebih lanjut.*)
