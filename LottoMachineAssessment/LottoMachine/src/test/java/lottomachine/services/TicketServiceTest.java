package lottomachine.services;

import lottomachine.InvalidAmountException;
import lottomachine.constants.*;
import lottomachine.models.LottoTicket;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TicketServiceTest {
    private TicketService ticketService;
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        paymentService = new PaymentService();
        ticketService = new TicketService(50, 6, paymentService);
    }

    @Test
    public void testSelectSingleLotto() throws InvalidAmountException {
        paymentService.acceptPayment(10);
        List<Integer> userNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        ticketService.selectTicket(TicketTypeConstants.SINGLE_LOTTO, userNumbers);
        assertEquals(1, ticketService.getTickets().size());
        assertEquals(TicketTypeConstants.SINGLE_LOTTO, ticketService.getTickets().get(0).getType());
    }

    @Test
    public void testSelectRandomLotto() throws InvalidAmountException {
        paymentService.acceptPayment(10);
        ticketService.selectTicket(TicketTypeConstants.RANDOM_LOTTO, null);
        assertEquals(1, ticketService.getTickets().size());
        assertEquals(TicketTypeConstants.RANDOM_LOTTO, ticketService.getTickets().get(0).getType());
    }

    @Test
    public void testSelectQuickFive() throws InvalidAmountException {
        paymentService.acceptPayment(10);
        paymentService.acceptPayment(10);
        paymentService.acceptPayment(5);
        List<Integer> userNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        ticketService.selectTicket(TicketTypeConstants.QUICK_FIVE, userNumbers);
        assertEquals(5, ticketService.getTickets().size());
        assertEquals(TicketTypeConstants.QUICK_FIVE, ticketService.getTickets().get(0).getType());
    }

    @Test
    public void testSelectRandomFive() throws InvalidAmountException {
        paymentService.acceptPayment(10);
        paymentService.acceptPayment(10);
        paymentService.acceptPayment(5);
        ticketService.selectTicket(TicketTypeConstants.RANDOM_FIVE, null);
        assertEquals(5, ticketService.getTickets().size());
        assertEquals(TicketTypeConstants.RANDOM_FIVE, ticketService.getTickets().get(0).getType());
    }

    @Test
    public void testClearTickets() throws InvalidAmountException {
        paymentService.acceptPayment(10);
        List<Integer> userNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        ticketService.selectTicket(TicketTypeConstants.SINGLE_LOTTO, userNumbers);
        ticketService.clearTickets();
        assertTrue(ticketService.getTickets().isEmpty());
    }

    @Test
    public void testCalculateWinnings() throws InvalidAmountException {
        paymentService.acceptPayment(10);
        List<Integer> userNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        ticketService.selectTicket(TicketTypeConstants.SINGLE_LOTTO, userNumbers);
        List<Integer> drawnNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        int totalWinnings = ticketService.calculateWinnings(drawnNumbers);
        assertTrue(totalWinnings > 0);

        // Verify the winnings for each ticket
        for (LottoTicket lottoTicket : ticketService.getTickets()) {
            assertTrue(lottoTicket.getWinnings() > 0);
        }
    }
}