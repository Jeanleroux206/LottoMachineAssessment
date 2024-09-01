package lottomachine.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LottoUtils {
    public static List<Integer> generateRandomDrawnNumbers(int totalBallsDrawn, int totalBalls) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= totalBalls; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers.subList(0, totalBallsDrawn);
    }
}