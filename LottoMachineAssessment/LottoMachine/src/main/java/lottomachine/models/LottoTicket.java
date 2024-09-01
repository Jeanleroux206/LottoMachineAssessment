package lottomachine.models;

import java.util.List;

public class LottoTicket {
    private String type;
    private List<Integer> numbers;
    private int winnings;

    public LottoTicket(String type, List<Integer> numbers) {
        this.type = type;
        this.numbers = numbers;
        this.winnings = 0;
    }

    public String getType() {
        return type;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public int getWinnings() {
        return winnings;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }
}