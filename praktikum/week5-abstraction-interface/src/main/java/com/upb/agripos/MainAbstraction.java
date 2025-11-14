package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.kontrak.Receiptable;
import main.java.com.upb.agripos.model.pembayaran.Cash;
import main.java.com.upb.agripos.model.pembayaran.EWallet;
import main.java.com.upb.agripos.model.pembayaran.Pembayaran;
import main.java.com.upb.agripos.util.CreditBy;

public class MainAbstraction {
    public static void main(String[] args) {

        Pembayaran cash = new Cash("INV-001", 100000, 150000);
        Pembayaran ew   = new EWallet("INV-002", 200000, "GOPAY", "123456");

        // menggunakan interface
        System.out.println(((Receiptable) cash).cetakStruk());
        System.out.println(((Receiptable) ew).cetakStruk());

        CreditBy.print("240202903", "Mohamad Gilang Rizki Riomdona");
    }
}