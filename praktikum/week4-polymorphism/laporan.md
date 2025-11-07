# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: [Tuliskan judul topik, misalnya "Class dan Object"]

## Identitas
- Nama  : [Mohamad Gilang Rizki Riomdona]
- NIM   : [240202903]
- Kelas : [3 IKRB]

---

## Tujuan
- Mahasiswa mampu **menjelaskan konsep polymorphism** dalam OOP.  
- Mahasiswa mampu **membedakan method overloading dan overriding**.  
- Mahasiswa mampu **mengimplementasikan polymorphism (overriding, overloading, dynamic binding)** dalam program.  
- Mahasiswa mampu **menganalisis contoh kasus polymorphism** pada sistem nyata (Agri-POS).  

---

## Dasar Teori
Polymorphism berarti “banyak bentuk” dan memungkinkan objek yang berbeda merespons panggilan method yang sama dengan cara yang berbeda.  
1. **Overloading** → mendefinisikan method dengan nama sama tetapi parameter berbeda.  
2. **Overriding** → subclass mengganti implementasi method dari superclass.  
3. **Dynamic Binding** → pemanggilan method ditentukan saat runtime, bukan compile time.

Dalam konteks Agri-POS, misalnya:  
- Method `getInfo()` pada `Produk` dioverride oleh `Benih`, `Pupuk`, `AlatPertanian` untuk menampilkan detail spesifik.  
- Method `tambahStok()` bisa dibuat overload dengan parameter berbeda (int, double).  

---

## Langkah Praktikum
(Tuliskan Langkah-langkah dalam prakrikum, contoh:
1. Langkah-langkah yang dilakukan (setup, coding, run).  
2. File/kode yang dibuat.  
3. Commit message yang digunakan.)

---

## Kode Program
1. **Overloading**  
   - Tambahkan method `tambahStok(int jumlah)` dan `tambahStok(double jumlah)` pada class `Produk`.  

2. **Overriding**  
   - Tambahkan method `getInfo()` pada superclass `Produk`.  
   - Override method `getInfo()` pada subclass `Benih`, `Pupuk`, dan `AlatPertanian`.  

3. **Dynamic Binding**  
   - Buat array `Produk[] daftarProduk` yang berisi objek `Benih`, `Pupuk`, dan `AlatPertanian`.  
   - Loop array tersebut dan panggil `getInfo()`. Perhatikan bagaimana Java memanggil method sesuai jenis objek aktual.  

4. **Main Class**  
   - Buat `MainPolymorphism.java` untuk mendemonstrasikan overloading, overriding, dan dynamic binding.  

5. **CreditBy**  
   - Tetap panggil `CreditBy.print("<NIM>", "<Nama>")`.  

6. **Commit dan Push**  
   - Commit dengan pesan: `week4-polymorphism`.  
 

```java
// Benih.java
package main.java.com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas;

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    @Override
    public String getInfo() {
        return "Benih: " + super.getInfo() + ", Varietas: " + varietas;
    }
}

//ObatHama.java
package main.java.com.upb.agripos.model;

public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }

    public void tambahStok(double jumlah) {
        this.stok += (int) jumlah;
    }


    public String getInfo() {
        return "Produk: " + nama + " (Kode: " + kode + ")";
    }
}

//Produk.java
package main.java.com.upb.agripos.model;

public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }

    public void tambahStok(double jumlah) {
        this.stok += (int) jumlah;
    }


    public String getInfo() {
        return "Produk: " + nama + " (Kode: " + kode + ")";
    }
}

//Pupuk.java
package main.java.com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis;

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    @Override
    public String getInfo() {
        return "Pupuk: " + super.getInfo() + ", Jenis: " + jenis;
    }

}

//CreditBy.java
package main.java.com.upb.agripos.util;

public class CreditBy {
    public static void print(String nim, String nama) {
        System.out.println("\ncredit by: " + nim + " - " + nama);
    }
}

//MainPolymorphism
package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.AlatPertanian;
import main.java.com.upb.agripos.model.Benih;
import main.java.com.upb.agripos.model.ObatHama;
import main.java.com.upb.agripos.model.Produk;
import main.java.com.upb.agripos.model.Pupuk;
import main.java.com.upb.agripos.util.CreditBy;

public class MainPolymorphism {
    public static void main(String[] args) {
        Produk[] daftarProduk = {
            new Benih("BNH-001", "Benih Padi IR64", 25000, 100, "IR64"),
            new Pupuk("PPK-101", "Pupuk Urea", 350000, 40, "Urea"),
            new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja"),
            new ObatHama("OBH-301", "Furadan 3GR", 50000, 25, "Carbofuran")
        };

        System.out.println("=== Info Produk Pertanian (Polymorphism) ===");
        for (Produk p : daftarProduk) {
            System.out.println(p.getInfo()); // Dynamic Binding
        }

        CreditBy.print ("240202903", "Mohamad Gilang Rizki Riomdona");
    }
}
```

---

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](HasilPolymorphism.png)
)
---

## Analisis
(
- Jelaskan bagaimana kode berjalan.  
   Program di atas merupakan contoh penerapan konsep polimorfisme (polymorphism) dalam pemrograman berorientasi objek menggunakan Java. Program ini terdiri dari beberapa kelas yang merepresentasikan berbagai jenis produk pertanian seperti Benih, Pupuk, AlatPertanian, dan ObatHama, yang semuanya merupakan turunan dari kelas induk Produk. Kelas Produk berisi atribut umum seperti kode, nama, harga, dan stok, serta menyediakan metode dasar seperti getInfo() dan tambahStok(). Setiap kelas turunan menambahkan atribut dan perilaku khusus sesuai jenis produknya, misalnya kelas Benih menambahkan atribut varietas, sedangkan kelas Pupuk menambahkan atribut jenis.

   Pada kelas MainPolymorphism, program membuat sebuah array bertipe Produk yang berisi objek dari berbagai kelas turunan. Saat program melakukan iterasi untuk menampilkan informasi produk dengan memanggil p.getInfo(), Java secara otomatis menentukan versi metode getInfo() mana yang dijalankan sesuai dengan jenis objek sebenarnya — inilah yang disebut dynamic binding atau runtime polymorphism. Dengan cara ini, meskipun semua objek disimpan dalam array bertipe Produk, setiap objek tetap menampilkan informasi yang sesuai dengan kelas aslinya. Setelah semua data produk ditampilkan, program memanggil metode CreditBy.print() untuk menampilkan informasi pembuat program. Secara keseluruhan, kode ini memperlihatkan bagaimana konsep pewarisan, enkapsulasi, dan polimorfisme dapat bekerja bersama untuk membuat program lebih fleksibel, terstruktur, dan mudah dikembangkan.

- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya. 
   Perbedaan utama antara kode minggu kemarin dan minggu ini terletak pada konsep pemrograman berorientasi objek yang ditekankan. Pada minggu kemarin, program lebih menonjolkan konsep inheritance (pewarisan), yaitu bagaimana kelas-kelas seperti Benih, Pupuk, dan AlatPertanian mewarisi atribut dan metode dari kelas induk Produk. Setiap kelas turunan memiliki metode sendiri bernama deskripsi() untuk menampilkan informasi produk yang bersifat spesifik, dan setiap objek dipanggil secara langsung dalam program utama. Pendekatan ini menunjukkan bagaimana subclass dapat menambahkan atribut atau perilaku baru di luar yang dimiliki kelas induk. Sementara itu, pada minggu ini, fokusnya bergeser ke penerapan polymorphism (polimorfisme), di mana berbagai objek turunan dari Produk disimpan dalam satu array bertipe Produk, kemudian diproses bersama menggunakan perulangan. Saat metode getInfo() dipanggil, Java secara otomatis menyesuaikan dan menjalankan versi metode yang sesuai dengan kelas aslinya. Hal ini memperlihatkan bagaimana satu referensi bertipe umum (Produk) dapat mewakili berbagai objek dengan perilaku yang berbeda. Dengan demikian, minggu kemarin menitikberatkan pada pewarisan struktur antar kelas, sedangkan minggu ini menekankan perbedaan perilaku dinamis antar objek melalui polimorfisme. 

- Kendala yang dihadapi dan cara mengatasinya.  
   Kendala yang saya hadapi dalam pembuatan program ini ada pada bagian pengaturan package dan pembuatan metode override. Pada awalnya, program tidak dapat dijalankan karena struktur package belum sesuai dengan letak file di folder proyek. Misalnya, kelas berada dalam folder tertentu tetapi nama package di dalam kode tidak sama, sehingga Java tidak dapat menemukan kelas yang dimaksud saat proses kompilasi. Untuk mengatasinya, saya memastikan bahwa nama package di setiap file sesuai dengan struktur folder di dalam proyek, misalnya package agripos.model; untuk file yang berada di dalam folder agripos/model. Selain itu, saya juga sempat mengalami kesulitan saat membuat override method, yaitu ketika ingin menimpa metode dari kelas induk di kelas turunan. Kesalahan terjadi karena penulisan metode tidak sepenuhnya sama dengan metode induknya, sehingga Java tidak mengenalinya sebagai override. Masalah ini saya atasi dengan menambahkan anotasi @Override di atas metode turunan dan memastikan nama, parameter, serta tipe kembalian metode sama persis dengan yang ada di kelas induk. Setelah itu, program dapat berjalan dengan baik dan menampilkan hasil sesuai yang diharapkan.
)
---

## Kesimpulan
(*Kesimpulan dari praktikum minggu ini adalah bahwa konsep polimorfisme (polymorphism) memungkinkan satu referensi dari kelas induk (Produk) untuk digunakan oleh berbagai objek turunan seperti Benih, Pupuk, AlatPertanian, dan ObatHama. Dengan demikian, meskipun semua objek disimpan dalam satu array bertipe Produk, setiap objek tetap dapat menampilkan perilaku yang berbeda sesuai dengan kelas aslinya ketika metode getInfo() dipanggil. Hal ini menunjukkan kekuatan dari pemrograman berorientasi objek dalam membuat kode yang lebih fleksibel, efisien, dan mudah dikembangkan. Melalui praktikum ini juga dapat dipahami bahwa polymorphism bekerja bersama dengan konsep inheritance untuk mendukung prinsip reusability (dapat digunakan kembali) dan extensibility (mudah dikembangkan) dalam pembuatan program berbasis objek*).

---

## Quiz
(1. [Tuliskan kembali pertanyaan 1 dari panduan]  
   **Jawaban:** …  

2. [Tuliskan kembali pertanyaan 2 dari panduan]  
   **Jawaban:** …  

3. [Tuliskan kembali pertanyaan 3 dari panduan]  
   **Jawaban:** …  )
