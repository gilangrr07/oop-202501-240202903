package agripos.model;

public class AlatPertanian extends Produk {
    private String material;

    public AlatPertanian(String kode, String nama, double harga, int stok, String material) {
        super(kode, nama, harga, stok);
        this.material = material;
    }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    
    public void deskripsi(){
        System.err.println("Nama Barang : "+ getNama() + "\n Material yang dipakai : " + getMaterial() + "\n harga cangkul :" + getHarga() + "\n");
    }
}
