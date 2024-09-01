package lottomachine.services.interfaces;

import lottomachine.InvalidAmountException;

import java.util.Map;

public interface IPaymentService {
    void acceptPayment(int amount) throws InvalidAmountException;
    int getBalance();
    Map<Integer, Integer> withdrawFunds() throws InvalidAmountException;
}