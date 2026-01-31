<<<<<<< HEAD
package com.upb.agripos.exception;

public class InsufficientPaymentException extends Exception {
    private double totalAmount;
    private double paidAmount;

    public InsufficientPaymentException(double totalAmount, double paidAmount) {
        super(String.format("Pembayaran tidak cukup. Total: Rp %.2f, Dibayar: Rp %.2f", 
            totalAmount, paidAmount));
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
    }

    public double getTotalAmount() { return totalAmount; }
    public double getPaidAmount() { return paidAmount; }
}
=======
package com.upb.agripos.exception;

public class InsufficientPaymentException extends Exception {
    private double totalAmount;
    private double paidAmount;

    public InsufficientPaymentException(double totalAmount, double paidAmount) {
        super(String.format("Pembayaran tidak cukup. Total: Rp %.2f, Dibayar: Rp %.2f", 
            totalAmount, paidAmount));
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
    }

    public double getTotalAmount() { return totalAmount; }
    public double getPaidAmount() { return paidAmount; }
}
>>>>>>> 4e34a79db5d9380e3d045243de31f412c2766f3f
