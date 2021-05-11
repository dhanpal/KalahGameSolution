package backbase.quiz.solution.utils;

import java.util.Arrays;
import java.util.List;

public class Constants {
	
	public static final Integer STONES = 6;
	public static final Integer PITS = 14;
	public static final Integer PAYER_1_KALAH = 7;
	public static final Integer PAYER_2_KALAH = 14;
	public static final List<Integer> PLAYER_1_PIT = Arrays.asList(1, 2, 3, 4, 5, 6, PAYER_1_KALAH);
	public static final List<Integer> PLAYER_2_PIT = Arrays.asList(8, 9, 10, 11, 12, 13, PAYER_2_KALAH);
}
