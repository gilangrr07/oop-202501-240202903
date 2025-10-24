package agripos;

import agripos.model.AlatPertanian;
import agripos.model.Benih;
import agripos.model.Pupuk;
import agripos.util.CreditBy;

public class MainInheritance {
    public static void main(String[] args) {
        Benih b = new Benih("BNH-001", "Benih Padi IR64", 25000, 100, "IR64");
        Pupuk p = new Pupuk("PPK-101", "Pupuk Urea", 350000, 40, "Urea");
        AlatPertanian a = new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja");

        b.deskripsi();
        p.deskripsi();
        a.deskripsi();

        CreditBy.print("<240202903>", "<Mohamad Gilang Rizki Riomdona>");
    }
}
