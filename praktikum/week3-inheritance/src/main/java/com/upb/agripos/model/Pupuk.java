package agripos.model;

public class Pupuk extends Produk {
    private String jenis;

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

     public void deskripsi(){
        System.err.println("Nama Barang : "+ getNama() + "\n Jenis pupuk : " + getJenis() + "\n harga pupuk/kg :" + getHarga() + "\n");
    }
}
