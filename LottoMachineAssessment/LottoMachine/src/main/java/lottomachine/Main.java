package lottomachine;

import lottomachine.constants.*;
import lottomachine.models.*;
import lottomachine.utils.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean requestCancelled = false;

        LottoMachine lottoMachine = new LottoMachine();

        // Prompt the user to select a lotto and read the input
        System.out.println("Select a lotto. Enter total number of balls and number of balls to be drawn:");
        int totalBalls = scanner.nextInt();
        int totalBallsDrawn = scanner.nextInt();
        lottoMachine.selectLotto(totalBalls, totalBallsDrawn);

        // Accept payments from the user
        requestCancelled = processPayments(scanner, lottoMachine);

        // If the request is not cancelled, proceed to ticket selection
        if (!requestCancelled) {
            requestCancelled = processTicketSelection(scanner, lottoMachine, totalBalls, totalBallsDrawn);
        }

        // If the request is not cancelled, simulate a drawing of numbers and calculate winnings
        if (!requestCancelled) {
            List<Integer> drawnNumbers = LottoUtils.generateRandomDrawnNumbers(totalBallsDrawn, totalBalls);
            System.out.println("Drawn Numbers: " + drawnNumbers);
            int totalWinnings = lottoMachine.calculateWinnings(drawnNumbers);
            System.out.println("Total Winnings: " + totalWinnings);

            // Display winnings for each ticket
            for (LottoTicket lottoTicket : lottoMachine.getTickets()) {
                System.out.println("Ticket Type: " + lottoTicket.getType());
                System.out.println("Numbers: " + lottoTicket.getNumbers());
                System.out.println("Winnings: " + lottoTicket.getWinnings());
            }
        }

        // Withdraw funds
        Map<Integer, Integer> withdrawnDenominations = lottoMachine.withdrawFunds();
        System.out.println("Withdrawn Denominations: " + withdrawnDenominations);

        scanner.close();
    }

    private static boolean processPayments(Scanner scanner, LottoMachine lottoMachine) {
        System.out.println("Insert coins/notes (1, 2, 5, 10, 50, 100).");
        System.out.println("Type 0 to stop or 'cancel' to cancel the request:");
        scanner.nextLine();

        while (true) {
            String input = scanner.nextLine().trim();
            // Check if the user wants to cancel the request
            if (input.equalsIgnoreCase("cancel")) {
                System.out.println("Request cancelled. Current Balance: " + lottoMachine.getBalance());
                return true;
            }
            int amount;
            try {
                amount = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid amount or 'cancel' to cancel the request:");
                continue;
            }
            if (amount == 0) break;

            // Accept the payment and update the balance
            lottoMachine.acceptPayment(amount);
            System.out.println("Accepted payment: " + amount);
            System.out.println("Current Balance: " + lottoMachine.getBalance());
        }
        return false;
    }

    private static boolean processTicketSelection(Scanner scanner, LottoMachine lottoMachine, int totalBalls, int totalBallsDrawn) {
        System.out.println("Select ticket type (Single Lotto, Random Lotto, Quick Five, Random Five).");
        System.out.println("Type 'done' to finish or 'cancel' to cancel the request:");
        boolean requestCancelled = false;

        while (true) {
            String type = scanner.nextLine().trim();
            if (type.equalsIgnoreCase("done")) break;
            // Check if the user wants to cancel the request
            if (type.equalsIgnoreCase("cancel")) {
                System.out.println("Request cancelled. Current Balance: " + lottoMachine.getBalance());
                requestCancelled = true;
                break;
            }
            boolean ticketSelected = handleTicketSelection(scanner, lottoMachine, type, totalBalls, totalBallsDrawn);
            if (requestCancelled) {
                System.out.println("Request cancelled. Current Balance: " + lottoMachine.getBalance());
                break;
            }
            if (ticketSelected) {
                // Display the selected tickets and remaining balance
                System.out.println("Selected Tickets:");
                for (LottoTicket lottoTicket : lottoMachine.getTickets()) {
                    System.out.println("Ticket Type: " + lottoTicket.getType());
                    System.out.println("Numbers: " + lottoTicket.getNumbers());
                    System.out.println("Winnings: " + lottoTicket.getWinnings());
                }

                System.out.println("Remaining Balance: " + lottoMachine.getBalance());
                System.out.println("Would you like to buy another ticket? Select ticket type (Single Lotto, Random Lotto, Quick Five, Random Five).");
                System.out.println("Type 'done' to finish or 'cancel' to cancel the request:");
            }
        }
        return requestCancelled;
    }

    // Handle ticket types
    private static boolean handleTicketSelection(Scanner scanner, LottoMachine lottoMachine, String type, int totalBalls, int totalBallsDrawn) {
        boolean ticketSelected = false;
        if (type.equalsIgnoreCase(TicketTypeConstants.SINGLE_LOTTO) || type.equalsIgnoreCase(TicketTypeConstants.QUICK_FIVE)) {
            ticketSelected = handleUserSelectedTicket(scanner, lottoMachine, type, totalBalls, totalBallsDrawn);
        } else if (type.equalsIgnoreCase(TicketTypeConstants.RANDOM_LOTTO) || type.equalsIgnoreCase(TicketTypeConstants.RANDOM_FIVE)) {
            ticketSelected = handleRandomTicket(lottoMachine, type);
        } else {
            System.out.println("Invalid ticket type. Please select a valid ticket type (Single Lotto, Random Lotto, Quick Five, Random Five).");
        }
        return ticketSelected;
    }

    private static boolean handleUserSelectedTicket(Scanner scanner, LottoMachine lottoMachine, String type, int totalBalls, int totalBallsDrawn) {
        System.out.println("Enter " + totalBallsDrawn + " numbers between 1 and " + totalBalls + ":");
        List<Integer> userNumbers = new ArrayList<>();
        for (int i = 0; i < totalBallsDrawn; i++) {
            int number = scanner.nextInt();
            // Check if the number is within the valid range and not a duplicate
            if (number >= 1 && number <= totalBalls && !userNumbers.contains(number)) {
                userNumbers.add(number);
            } else {
                System.out.println("Invalid or duplicate number. Please enter a unique number between 1 and " + totalBalls + ":");
                i--; // Decrement the counter to retry the input
            }
        }
        scanner.nextLine();
        lottoMachine.selectTicket(type, userNumbers);
        System.out.println("Selected numbers: " + userNumbers);
        return true;
    }

    private static boolean handleRandomTicket(LottoMachine lottoMachine, String type) {
        lottoMachine.selectTicket(type, null);
        return true;
    }
}