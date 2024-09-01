package lottomachine;

import lottomachine.constants.*;
import lottomachine.models.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LottoMachineTest {
    private LottoMachine lottoMachine;

    @BeforeEach
    public void setUp() {
        lottoMachine = new LottoMachine();
        lottoMachine.selectLotto(50, 6); // Set default values for testing
    }

    @Test
    public void testAcceptPayment() throws InvalidAmountException {
        lottoMachine.acceptPayment(10);
        assertEquals(10, lottoMachine.getBalance());
    }

    @Test
    public void testSelectSingleLotto() throws InvalidAmountException {
        lottoMachine.acceptPayment(10);
        List<Integer> userNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        lottoMachine.selectTicket(TicketTypeConstants.SINGLE_LOTTO, userNumbers);
        assertEquals(1, lottoMachine.getTickets().size());
        assertEquals(TicketTypeConstants.SINGLE_LOTTO, lottoMachine.getTickets().get(0).getType());
    }

    @Test
    public void testSelectRandomLotto() throws InvalidAmountException {
        lottoMachine.acceptPayment(10);
        lottoMachine.selectTicket(TicketTypeConstants.RANDOM_LOTTO, null);
        assertEquals(1, lottoMachine.getTickets().size());
        assertEquals(TicketTypeConstants.RANDOM_LOTTO, lottoMachine.getTickets().get(0).getType());
    }

    @Test
    public void testSelectQuickFive() throws InvalidAmountException {
        lottoMachine.acceptPayment(10);
        lottoMachine.acceptPayment(10);
        lottoMachine.acceptPayment(5);
        List<Integer> userNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        lottoMachine.selectTicket(TicketTypeConstants.QUICK_FIVE, userNumbers);
        assertEquals(5, lottoMachine.getTickets().size());
        assertEquals(TicketTypeConstants.QUICK_FIVE, lottoMachine.getTickets().get(0).getType());
    }

    @Test
    public void testSelectRandomFive() throws InvalidAmountException {
        lottoMachine.acceptPayment(10);
        lottoMachine.acceptPayment(10);
        lottoMachine.acceptPayment(5);
        lottoMachine.selectTicket(TicketTypeConstants.RANDOM_FIVE, null);
        assertEquals(5, lottoMachine.getTickets().size());
        assertEquals(TicketTypeConstants.RANDOM_FIVE, lottoMachine.getTickets().get(0).getType());
    }

    @Test
    public void testCancelRequest() throws InvalidAmountException {
        lottoMachine.acceptPayment(10);
        lottoMachine.selectTicket(TicketTypeConstants.SINGLE_LOTTO, Arrays.asList(1, 2, 3, 4, 5, 6));
        lottoMachine.cancelRequest();
        assertEquals(0, lottoMachine.getBalance());
        assertTrue(lottoMachine.getTickets().isEmpty());
    }

    @Test
    public void testCalculateWinnings() throws InvalidAmountException {
        lottoMachine.acceptPayment(10);
        lottoMachine.selectTicket(TicketTypeConstants.SINGLE_LOTTO, Arrays.asList(1, 2, 3, 4, 5, 6));
        List<Integer> drawnNumbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        int totalWinnings = lottoMachine.calculateWinnings(drawnNumbers);
        assertTrue(totalWinnings > 0);

        // Verify the winnings for each ticket
        for (LottoTicket lottoTicket : lottoMachine.getTickets()) {
            assertTrue(lottoTicket.getWinnings() > 0);
        }
    }

    @Test
    public void testWithdrawFunds() throws InvalidAmountException {
        lottoMachine.acceptPayment(50);
        lottoMachine.withdrawFunds();
        assertEquals(0, lottoMachine.getBalance());
    }
}