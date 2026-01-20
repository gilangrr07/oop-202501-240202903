package com.upb.agripos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Transaction - Model untuk transaksi checkout
 * Integrasi: Bab 5 (Abstraction), Bab 7 (Collections)
 */
public class Transaction implements Receiptable {
    
    private String invoiceNo;
    private LocalDateTime timestamp;
    private List<CartItem> items;
    private double total;
    private String cashierName;

    public Transaction(String invoiceNo, List<CartItem> items, double total, String cashierName) {
        this.invoiceNo = invoiceNo;
        this.timestamp = LocalDateTime.now();
        this.items = items;
        this.total = total;
        this.cashierName = cashierName;
    }

    public String getInvoiceNo() { return invoiceNo; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public List<CartItem> getItems() { return items; }
    public double getTotal() { return total; }
    public String getCashierName() { return cashierName; }

    @Override
    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        receipt.append("\n");
        receipt.append("==================================================\n");
        receipt.append(" |          🌾 AGRI-POS TOKO PERTANIAN 🌾       |\n");
        receipt.append(" |     Jl. Ronggowasito No. 129, Kebumen         |\n");
        receipt.append(" |          Telp: (0274) 123-4567                |\n");
        receipt.append("===================================================\n");
        receipt.append(String.format("║ INVOICE   : %-35s║\n", invoiceNo));
        receipt.append(String.format("║ TANGGAL   : %-35s║\n", timestamp.format(formatter)));
        receipt.append(String.format("║ KASIR     : %-35s║\n", cashierName));
        receipt.append("==================================================\n");
        receipt.append("| ITEM PEMBELIAN                                 |\n");
        receipt.append("==================================================\n");
        
        // Detail items
        for (CartItem item : items) {
            String itemName = item.getProduct().getName();
            int qty = item.getQuantity();
            double price = item.getProduct().getPrice();
            double subtotal = item.getSubtotal();
            
            // Truncate nama jika terlalu panjang
            if (itemName.length() > 25) {
                itemName = itemName.substring(0, 22) + "...";
            }
            
            receipt.append(String.format("║ %-25s                     ║\n", itemName));
            receipt.append(String.format("║   %d x Rp%,15.2f = Rp%,13.2f ║\n", 
                qty, price, subtotal));
        }
        
        receipt.append("==================================================\n");
        receipt.append(String.format("║ TOTAL BELANJA          Rp%,20.2f ║\n", total));
        receipt.append("==================================================\n");
        receipt.append(" |                                               |\n");
        receipt.append(" |    ✓ TERIMA KASIH ATAS KUNJUNGAN ANDA ✓      |\n");
        receipt.append(" |        Semoga Panen Anda Melimpah! 🌾        |\n");
        receipt.append(" |                                               |\n");
        receipt.append("==================================================\n");
        
        return receipt.toString();
    }

    /**
     * Generate nomor invoice otomatis
     * Format: INV-YYYYMMDD-XXXXXX
     */
    public static String generateInvoiceNo() {
        LocalDateTime now = LocalDateTime.now();
        String datePart = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomPart = (int) (Math.random() * 999999);
        return String.format("INV-%s-%06d", datePart, randomPart);
    }
}