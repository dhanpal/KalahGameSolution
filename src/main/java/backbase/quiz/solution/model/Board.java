package backbase.quiz.solution.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backbase.quiz.solution.utils.Constants;
import backbase.quiz.solution.utils.Rules;
import lombok.Getter;

@Getter
public class Board {

	private Game game;
	private Map<Integer, Integer> myBoard;

	/**
	 * Constructor for a new board It starts all the pit with fixed number of
	 * stones
	 * 
	 * @param game
	 *            current game
	 */
	public Board(Game game) {
		this.game = game;
		myBoard = new HashMap<>();
		myBoard.put(1, Constants.STONES);
		myBoard.put(2, Constants.STONES);
		myBoard.put(3, Constants.STONES);
		myBoard.put(4, Constants.STONES);
		myBoard.put(5, Constants.STONES);
		myBoard.put(6, Constants.STONES);
		myBoard.put(Constants.PAYER_1_KALAH, 0);
		myBoard.put(8, Constants.STONES);
		myBoard.put(9, Constants.STONES);
		myBoard.put(10, Constants.STONES);
		myBoard.put(11, Constants.STONES);
		myBoard.put(12, Constants.STONES);
		myBoard.put(13, Constants.STONES);
		myBoard.put(Constants.PAYER_2_KALAH, 0);
	}

	/**
	 * Makes a move and updates the stone count in each pit Updates the current
	 * game board with updated values
	 * 
	 * @param pitid Current Pit id
	 */
	public void move(Integer pitid) {
		boolean isPlayerOne = isPlayerOne(pitid);
		boolean isPlayerTwo = isPlayerTwo(pitid);
		Integer stones = getStones(pitid);
		cleanCurrentPit(pitid);
		List<Integer> eligiblePits = getEligiblePits(pitid, stones, isPlayerOne, isPlayerTwo);
		if (eligiblePits.size() > 0) {
			updateBoard(eligiblePits, isPlayerOne, isPlayerTwo);
		}
	}

	/**
	 * Checks based on pit id if it's player one
	 * 
	 * @param pitid
	 * @return
	 */
	private boolean isPlayerOne(Integer pitid) {
		return Constants.PLAYER_1_PIT.contains(pitid);
	}

	/**
	 * Checks based on pit id if it's player two
	 * 
	 * @param pitid
	 * @return
	 */
	private boolean isPlayerTwo(Integer pitid) {
		return Constants.PLAYER_2_PIT.contains(pitid);
	}

	/**
	 * Returns number of stones in current pit
	 * 
	 * @param pitid
	 * @return
	 */
	private Integer getStones(Integer pitid) {
		return myBoard.get(pitid);
	}

	/**
	 * Removes all the stones from current pit
	 * 
	 * @param pitid
	 */
	private void cleanCurrentPit(Integer pitid) {
		if (myBoard.containsKey(pitid)) {
			myBoard.put(pitid, 0);
		}
	}

	/**
	 * Returns a list of all eligible pits which needs to be updated after
	 * making a move in the game
	 * 
	 * @param pitid
	 * @param stones
	 * @param isPlayerOne
	 * @param isPlayerTwo
	 * @return
	 */
	private List<Integer> getEligiblePits(Integer pitid, Integer stones, boolean isPlayerOne, boolean isPlayerTwo) {
		List<Integer> list = new ArrayList<Integer>();
		int nextPit = pitid + 1;

		// Iterate over all the stones
		for (int i = 0; i < stones; i++) {

			if (isPlayerOne) {
				// Check if next pit is either second players Kalah(that's why
				// =) or next pit exceeded the max number of pits
				if (nextPit >= Constants.PITS) {
					nextPit = 1;
				}
				if (!Constants.PAYER_2_KALAH.equals(nextPit)) {
					list.add(nextPit);
					nextPit++;
				}

			}
			if (isPlayerTwo) {
				// If next pit exceeds the max no of pits, reset it to beginning
				if (nextPit > Constants.PITS) {
					nextPit = 1;
				}
				// If it's player one, skip it
				if (Constants.PAYER_1_KALAH.equals(nextPit)) {
					nextPit++;
				}
				if (!Constants.PAYER_1_KALAH.equals(nextPit)) {
					list.add(nextPit);
					nextPit++;
				}
			}
		}

		return list;
	}

	/**
	 * Updates all the eligible pits with one stone
	 * And apply Kalah Rule
	 * @param eligiblePits
	 */
	private void updateBoard(List<Integer> eligiblePits, boolean isPlayerOne, boolean isPlayerTwo) {
		for (Integer key : eligiblePits) {
			if (myBoard.containsKey(key)) {
				Integer currentStones = myBoard.get(key);
				myBoard.put(key, currentStones + 1);
			}
		}
		applyRule(eligiblePits.get(eligiblePits.size() - 1), isPlayerOne, isPlayerTwo);
	}

	/**
	 * Apply kalah rule (Incase it's last move and pit is empty, you will collect all stones from opposite and current pit into your Kalah)
	 * @param pitId
	 * @param isPlayerOne
	 * @param isPlayerTwo
	 */
	private void applyRule(Integer pitId, boolean isPlayerOne, boolean isPlayerTwo) {
		if (myBoard.get(pitId).equals(1) && !pitId.equals(Constants.PAYER_1_KALAH)
				&& !pitId.equals(Constants.PAYER_2_KALAH)) {
			int oppositeKey = 0;
			int finalValue = 0;
			if (Constants.PLAYER_1_PIT.contains(pitId) && isPlayerOne) {
				oppositeKey = Rules.oppositePit.get(pitId);
				finalValue = myBoard.get(Constants.PAYER_1_KALAH) + myBoard.get(oppositeKey) + getStones(pitId);
				myBoard.put(Constants.PAYER_1_KALAH, finalValue);
			}
			if (Constants.PLAYER_2_PIT.contains(pitId) && isPlayerTwo) {
				oppositeKey = Rules.oppositePit.get(pitId);
				finalValue = myBoard.get(Constants.PAYER_2_KALAH) + myBoard.get(oppositeKey) + getStones(pitId);
				myBoard.put(Constants.PAYER_2_KALAH, finalValue);
			}
			cleanCurrentPit(pitId);
			cleanCurrentPit(oppositeKey);
		}
	}

}
