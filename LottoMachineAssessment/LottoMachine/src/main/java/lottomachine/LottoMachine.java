package lottomachine;

import java.util.*;

import lottomachine.interfaces.*;
import lottomachine.models.*;
import lottomachine.services.interfaces.*;
import lottomachine.services.*;

public class LottoMachine implements ILottoMachine {
    private int totalBalls;
    private int totalBallsDrawn;
    private IPaymentService paymentService;
    private ITicketService ticketService;

    public LottoMachine() {
        this.paymentService = new PaymentService();
    }

    @Override
    public void selectLotto(int totalBalls, int totalBallsDrawn) {
        this.totalBalls = totalBalls;
        this.totalBallsDrawn = totalBallsDrawn;
        this.ticketService = new TicketService(totalBalls, totalBallsDrawn, paymentService);
        System.out.println("Lotto selected with " + totalBalls + " balls and " + totalBallsDrawn + " balls to be drawn.");
    }

    //Accepts a payment and updates the balance.
    @Override
    public void acceptPayment(int amount) {
        try {
            paymentService.acceptPayment(amount);
        } catch (InvalidAmountException e) {
            System.err.println("Error accepting payment: " + e.getMessage());
        }
    }

    //Selects a ticket based on the type and user-provided numbers.
    @Override
    public void selectTicket(String type, List<Integer> userNumbers) {
        try {
            ticketService.selectTicket(type, userNumbers);
        } catch (InvalidAmountException e) {
            System.err.println("Error selecting ticket: " + e.getMessage());
        }
    }

    //Cancels the current request, withdraws funds, and clears selected tickets.
    @Override
    public void cancelRequest() {
        try {
            paymentService.withdrawFunds();
        } catch (InvalidAmountException e) {
            System.err.println("Error occurred trying to cancel user request: " + e.getMessage());
        }
        ticketService.clearTickets();
    }

    @Override
    public List<LottoTicket> getTickets() {
        return ticketService.getTickets();
    }

    @Override
    public int getBalance() {
        return paymentService.getBalance();
    }

    //Withdraws the funds and returns the denominations.
    @Override
    public Map<Integer, Integer> withdrawFunds() {
        try {
            System.out.println("Withdrawing: " + paymentService.getBalance());
            return paymentService.withdrawFunds();
        } catch (InvalidAmountException e) {
            System.err.println("Error occurred trying to withdraw user funds: " + e.getMessage());
            return Collections.emptyMap();
        }
    }

    //Calculates the total winnings based on the drawn numbers.
    @Override
    public int calculateWinnings(List<Integer> drawnNumbers) {
        return ticketService.calculateWinnings(drawnNumbers);
    }
}