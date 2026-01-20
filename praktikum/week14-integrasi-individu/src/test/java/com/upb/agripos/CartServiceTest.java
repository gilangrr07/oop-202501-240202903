package test.java.com.upb.agripos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upb.agripos.exception.DatabaseException;
import com.upb.agripos.exception.InsufficientStockException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;

/**
 * CartServiceTest - Unit Testing untuk CartService
 * Integrasi: Bab 10 (Unit Testing dengan JUnit)
 * 
 * NOTE: Test ini menggunakan in-memory objects tanpa database
 * Untuk test dengan database, gunakan integration test terpisah
 */
class CartServiceTest {

    private CartService cartService;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        // Gunakan constructor tanpa ProductService untuk unit test
        // (tidak perlu database connection di unit test)
        cartService = new CartService(new MockProductService());
        product1 = new Product("P01", "Pupuk Organik", 25000, 10);
        product2 = new Product("P02", "Benih Padi", 15000, 20);
    }

    /**
     * Mock ProductService untuk unit testing tanpa database
     */
    private static class MockProductService extends ProductService {
        @Override
        public void updateStock(Product product) throws DatabaseException, ValidationException {
            // Mock: tidak melakukan apa-apa, hanya validasi
            if (product == null) {
                throw new ValidationException("Produk tidak boleh null!");
            }
            if (product.getStock() < 0) {
                throw new ValidationException("Stok tidak boleh negatif!");
            }
            // Success - do nothing (mock)
        }
    }

    @Test
    void testAddToCart_Success() throws ValidationException, InsufficientStockException {
        cartService.addToCart(product1, 2);
        
        assertEquals(1, cartService.getItemCount());
        assertEquals(50000, cartService.getTotal());
    }

    @Test
    void testAddToCart_MultipleProducts() throws ValidationException, InsufficientStockException {
        cartService.addToCart(product1, 2);
        cartService.addToCart(product2, 3);
        
        assertEquals(2, cartService.getItemCount());
        assertEquals(95000, cartService.getTotal()); // (2*25000) + (3*15000)
    }

    @Test
    void testAddToCart_InvalidQuantity() {
        assertThrows(ValidationException.class, () -> {
            cartService.addToCart(product1, 0);
        });
        
        assertThrows(ValidationException.class, () -> {
            cartService.addToCart(product1, -5);
        });
    }

    @Test
    void testAddToCart_InsufficientStock() {
        assertThrows(InsufficientStockException.class, () -> {
            cartService.addToCart(product1, 15); // Stok hanya 10
        });
    }

    @Test
    void testAddToCart_NullProduct() {
        assertThrows(ValidationException.class, () -> {
            cartService.addToCart(null, 2);
        });
    }

    @Test
    void testRemoveFromCart() throws ValidationException, InsufficientStockException {
        cartService.addToCart(product1, 2);
        cartService.addToCart(product2, 3);
        
        assertEquals(2, cartService.getItemCount());
        
        cartService.removeFromCart("P01");
        
        assertEquals(1, cartService.getItemCount());
        assertEquals(45000, cartService.getTotal());
    }

    @Test
    void testClearCart() throws ValidationException, InsufficientStockException {
        cartService.addToCart(product1, 2);
        cartService.addToCart(product2, 3);
        
        assertFalse(cartService.isEmpty());
        
        cartService.clearCart();
        
        assertTrue(cartService.isEmpty());
        assertEquals(0, cartService.getTotal());
    }

    @Test
    void testCheckout_Success() throws ValidationException, InsufficientStockException, DatabaseException {
        cartService.addToCart(product1, 2);
        
        int originalStock = product1.getStock();
        
        Transaction transaction = cartService.checkout("Test Kasir");
        
        assertNotNull(transaction);
        assertEquals(originalStock - 2, product1.getStock());
        assertTrue(cartService.isEmpty());
        
        // Verify transaction details
        assertNotNull(transaction.getInvoiceNo());
        assertEquals(50000, transaction.getTotal());
        assertEquals("Test Kasir", transaction.getCashierName());
        
        // Verify receipt generation
        String receipt = transaction.generateReceipt();
        assertNotNull(receipt);
        assertTrue(receipt.contains("AGRI-POS"));
        assertTrue(receipt.contains(transaction.getInvoiceNo()));
    }

    @Test
    void testCheckout_EmptyCart() {
        assertThrows(IllegalStateException.class, () -> {
            cartService.checkout("Test Kasir");
        });
    }

    @Test
    void testGetTotal_EmptyCart() {
        assertEquals(0, cartService.getTotal());
    }

    @Test
    void testUpdateQuantity() throws ValidationException, InsufficientStockException {
        cartService.addToCart(product1, 2);
        
        double oldTotal = cartService.getTotal();
        
        cartService.updateQuantity("P01", 5);
        
        assertNotEquals(oldTotal, cartService.getTotal());
        assertEquals(125000, cartService.getTotal()); // 5 * 25000
    }

    @Test
    void testUpdateQuantity_InvalidQuantity() throws ValidationException, InsufficientStockException {
        cartService.addToCart(product1, 2);
        
        assertThrows(ValidationException.class, () -> {
            cartService.updateQuantity("P01", 0);
        });
    }

    @Test
    void testReceiptGeneration() throws ValidationException, InsufficientStockException, DatabaseException {
        cartService.addToCart(product1, 2);
        cartService.addToCart(product2, 3);
        
        Transaction transaction = cartService.checkout("Mohamad Gilang Rizki Riomdona");
        String receipt = transaction.generateReceipt();
        
        // Verify receipt contains essential elements
        assertTrue(receipt.contains("AGRI-POS"));
        assertTrue(receipt.contains("INVOICE"));
        assertTrue(receipt.contains(transaction.getInvoiceNo()));
        assertTrue(receipt.contains("Mohamad Gilang Rizki Riomdona"));
        assertTrue(receipt.contains("TOTAL BELANJA"));
        assertTrue(receipt.contains("TERIMA KASIH"));
        
        // Verify product names in receipt
        assertTrue(receipt.contains("Pupuk Organik") || receipt.contains("Pupuk Organik".substring(0, 10)));
        assertTrue(receipt.contains("Benih Padi") || receipt.contains("Benih Padi".substring(0, 10)));
    }

    @Test
    void testInvoiceNumberGeneration() {
        String invoice1 = Transaction.generateInvoiceNo();
        String invoice2 = Transaction.generateInvoiceNo();
        
        assertNotNull(invoice1);
        assertNotNull(invoice2);
        assertTrue(invoice1.startsWith("INV-"));
        assertTrue(invoice2.startsWith("INV-"));
        
        // Invoice numbers should be different (extremely high probability)
        assertNotEquals(invoice1, invoice2);
    }
}