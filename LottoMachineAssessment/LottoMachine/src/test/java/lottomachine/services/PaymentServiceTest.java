package lottomachine.services;

import lottomachine.InvalidAmountException;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        paymentService = new PaymentService();
    }

    @Test
    public void testAcceptPayment() throws InvalidAmountException {
        paymentService.acceptPayment(10);
        assertEquals(10, paymentService.getBalance());
    }

    @Test
    public void testAcceptPaymentInvalidAmount() {
        InvalidAmountException exception = assertThrows(InvalidAmountException.class, () -> paymentService.acceptPayment(3));
        assertEquals("Invalid coin/note supplied: 3 Accepted coins/notes (1, 2, 5, 10, 50, 100).", exception.getMessage());
    }

    @Test
    public void testAcceptPaymentNegativeAmount() throws InvalidAmountException {
        paymentService.acceptPayment(10);
        paymentService.acceptPayment(-5);
        assertEquals(5, paymentService.getBalance());
    }

    @Test
    public void testAcceptPaymentNegativeAmountInsufficientBalance() {
        InvalidAmountException exception = assertThrows(InvalidAmountException.class, () -> paymentService.acceptPayment(-10));
        assertEquals("Insufficient balance for refund", exception.getMessage());
    }

    @Test
    public void testGetBalance() throws InvalidAmountException {
        paymentService.acceptPayment(10);
        assertEquals(10, paymentService.getBalance());
    }

    @Test
    public void testWithdrawFunds() throws InvalidAmountException {
        paymentService.acceptPayment(10);
        Map<Integer, Integer> denominations = paymentService.withdrawFunds();
        assertEquals(0, paymentService.getBalance());
        assertEquals(1, denominations.get(10));
    }

    @Test
    public void testWithdrawFundsInsufficientBalance() {
        InvalidAmountException exception = assertThrows(InvalidAmountException.class, () -> paymentService.withdrawFunds());
        assertEquals("Insufficient funds within users account to withdraw.", exception.getMessage());
    }

    @Test
    public void testWithdrawFundsMultipleDenominations() throws InvalidAmountException {
        paymentService.acceptPayment(50);
        paymentService.acceptPayment(5);
        Map<Integer, Integer> denominations = paymentService.withdrawFunds();
        assertEquals(0, paymentService.getBalance());
        assertEquals(1, denominations.get(50));
        assertEquals(1, denominations.get(5));
    }
}