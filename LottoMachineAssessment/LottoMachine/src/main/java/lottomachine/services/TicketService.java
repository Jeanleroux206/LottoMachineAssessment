package lottomachine.services;

import java.util.*;
import lottomachine.constants.*;
import lottomachine.InvalidAmountException;
import lottomachine.models.*;
import lottomachine.services.interfaces.*;

public class TicketService implements ITicketService {
    private int totalBalls;
    private int totalBallsDrawn;
    private IPaymentService paymentService;
    private List<LottoTicket> lottoTickets;

    public TicketService(int totalBalls, int totalBallsDrawn, IPaymentService paymentService) {
        this.totalBalls = totalBalls;
        this.totalBallsDrawn = totalBallsDrawn;
        this.paymentService = paymentService;
        this.lottoTickets = new ArrayList<>();
    }

    //Selects a ticket based on the type and user-provided numbers.
    @Override
    public void selectTicket(String type, List<Integer> userNumbers) {
        System.out.println("Selecting ticket type: " + type);
        System.out.println("Current balance: " + paymentService.getBalance());

        try {
            if (paymentService.getBalance() <= 0) {
                throw new InvalidAmountException("Insufficient balance to buy a ticket.");
            }

            switch (type) {
                case TicketTypeConstants.SINGLE_LOTTO:
                    selectSingleLotto(userNumbers);
                    break;
                case TicketTypeConstants.RANDOM_LOTTO:
                    selectRandomLotto();
                    break;
                case TicketTypeConstants.QUICK_FIVE:
                    selectQuickFive(userNumbers);
                    break;
                case TicketTypeConstants.RANDOM_FIVE:
                    selectRandomFive();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid ticket type");
            }
        } catch (InvalidAmountException e) {
            System.err.println("Error selecting ticket: " + e.getMessage());
        }
    }

    //Selects a single lotto ticket with user-provided numbers.
    private void selectSingleLotto(List<Integer> userNumbers) throws InvalidAmountException {
        validateBalance(LottoConstants.SINGLE_LOTTO_COST);
        lottoTickets.add(new LottoTicket(TicketTypeConstants.SINGLE_LOTTO, userNumbers));
        paymentService.acceptPayment(-LottoConstants.SINGLE_LOTTO_COST);
    }

    //Selects a random lotto ticket.
    private void selectRandomLotto() throws InvalidAmountException {
        validateBalance(LottoConstants.RANDOM_LOTTO_COST);
        List<Integer> randomNumbers = generateRandomNumbers();
        lottoTickets.add(new LottoTicket(TicketTypeConstants.RANDOM_LOTTO, randomNumbers));
        paymentService.acceptPayment(-LottoConstants.RANDOM_LOTTO_COST);
    }

    //Selects five quick lotto tickets with user-provided numbers.
    private void selectQuickFive(List<Integer> userNumbers) throws InvalidAmountException {
        validateBalance(LottoConstants.QUICK_FIVE_COST);
        for (int i = 0; i < 5; i++) {
            lottoTickets.add(new LottoTicket(TicketTypeConstants.QUICK_FIVE, userNumbers));
        }
        paymentService.acceptPayment(-LottoConstants.QUICK_FIVE_COST);
    }

    //Selects five random lotto tickets.
    private void selectRandomFive() throws InvalidAmountException {
        validateBalance(LottoConstants.RANDOM_FIVE_COST);
        for (int i = 0; i < 5; i++) {
            List<Integer> randomNumbers = generateRandomNumbers();
            lottoTickets.add(new LottoTicket(TicketTypeConstants.RANDOM_FIVE, randomNumbers));
        }
        paymentService.acceptPayment(-LottoConstants.RANDOM_FIVE_COST);
    }

    //Validates if the current balance is sufficient for the given cost.
    private void validateBalance(int cost) throws InvalidAmountException {
        if (paymentService.getBalance() < cost) {
            throw new InvalidAmountException("Insufficient balance for " + cost);
        }
    }

    // Generates a list of random numbers based on the total balls and balls to be drawn.
    private List<Integer> generateRandomNumbers() {
        Set<Integer> numbersSet = new HashSet<>();
        Random rand = new Random();
        while (numbersSet.size() < totalBallsDrawn) {
            int num = rand.nextInt(totalBalls) + 1;
            numbersSet.add(num);
        }
        return new ArrayList<>(numbersSet);
    }

    @Override
    public void clearTickets() {
        lottoTickets.clear();
    }

    @Override
    public List<LottoTicket> getTickets() {
        return lottoTickets;
    }

    //Calculates the total winnings based on the drawn numbers.
    @Override
    public int calculateWinnings(List<Integer> drawnNumbers) {
        int totalWinnings = 0;
        for (LottoTicket lottoTicket : lottoTickets) {
            int correct = 0;
            for (int num : lottoTicket.getNumbers()) {
                if (drawnNumbers.contains(num)) {
                    correct++;
                }
            }
            if (correct > 2) {
                int winnings = (int) Math.pow(10, correct);
                lottoTicket.setWinnings(winnings);
                totalWinnings += winnings;
            }
        }
        return totalWinnings;
    }
}