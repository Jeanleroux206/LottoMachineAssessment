package lottomachine.services;

import lottomachine.InvalidAmountException;
import lottomachine.services.interfaces.*;

import java.util.*;

public class PaymentService implements IPaymentService {
    private static final Set<Integer> validAmountsSet = new HashSet<>(Arrays.asList(1, 2, 5, 10, 50, 100));
    private int balance;

    public PaymentService() {
        this.balance = 0;
    }

    //Accepts a payment and updates the balance.
    @Override
    public void acceptPayment(int amount) throws InvalidAmountException {
        if (validAmountsSet.contains(amount)) {
            balance += amount;
        } else if (amount < 0) {
            if (balance + amount >= 0) {
                balance += amount;
            } else {
                throw new InvalidAmountException("Insufficient balance for refund");
            }
        } else {
            throw new InvalidAmountException("Invalid coin/note supplied: " + amount + " Accepted coins/notes (1, 2, 5, 10, 50, 100).");
        }
    }

    @Override
    public int getBalance() {
        return balance;
    }

    //Withdraws the funds and returns the denominations.
    @Override
    public Map<Integer, Integer> withdrawFunds() throws InvalidAmountException {
        if (balance <= 0) {
            throw new InvalidAmountException("Insufficient funds within users account to withdraw.");
        }
        Map<Integer, Integer> denominations = new HashMap<>();
        int remainingBalance = balance;
        for (int denomination : validAmountsSet.stream().sorted(Collections.reverseOrder()).toList()) {
            int count = remainingBalance / denomination;
            if (count > 0) {
                denominations.put(denomination, count);
                remainingBalance %= denomination;
            }
        }
        balance = 0;
        return denominations;
    }
}