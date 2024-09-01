package lottomachine.interfaces;

import lottomachine.InvalidAmountException;
import lottomachine.models.LottoTicket;

import java.util.List;
import java.util.Map;

public interface ILottoMachine {
    void selectLotto(int N, int X);
    void acceptPayment(int amount) throws InvalidAmountException;
    void selectTicket(String type, List<Integer> userNumbers) throws InvalidAmountException;
    void cancelRequest();
    List<LottoTicket> getTickets();
    int getBalance();
    Map<Integer, Integer> withdrawFunds();
    int calculateWinnings(List<Integer> drawnNumbers);
}