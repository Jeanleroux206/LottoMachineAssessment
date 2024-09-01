package lottomachine.services.interfaces;

import lottomachine.InvalidAmountException;
import lottomachine.models.*;

import java.util.List;

public interface ITicketService {
    void selectTicket(String type, List<Integer> userNumbers) throws InvalidAmountException;
    List<LottoTicket> getTickets();
    void clearTickets();
    int calculateWinnings(List<Integer> drawnNumbers);
}