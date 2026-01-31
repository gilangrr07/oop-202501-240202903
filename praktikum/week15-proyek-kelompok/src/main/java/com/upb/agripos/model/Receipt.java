<<<<<<< HEAD
package com.upb.agripos.model;

public class Receipt {
    private Transaction transaction;
    private String kasirName;

    public Receipt(Transaction transaction, String kasirName) {
        this.transaction = transaction;
        this.kasirName = kasirName;
    }

    public Transaction getTransaction() { return transaction; }
    public String getKasirName() { return kasirName; }
}
=======
package com.upb.agripos.model;

public class Receipt {
    private Transaction transaction;
    private String kasirName;

    public Receipt(Transaction transaction, String kasirName) {
        this.transaction = transaction;
        this.kasirName = kasirName;
    }

    public Transaction getTransaction() { return transaction; }
    public String getKasirName() { return kasirName; }
}
>>>>>>> 4e34a79db5d9380e3d045243de31f412c2766f3f
