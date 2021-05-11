package backbase.quiz.solution.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import backbase.quiz.solution.exception.GameException;
import backbase.quiz.solution.model.Board;

@Repository
public class BoardRepository {

	protected static Map<String, Board> currentBoard = new HashMap<String, Board>();

	/**
	 * Updates the current status of the game
	 * with updated board
	 * @param id input
	 * @param board Updated board
	 */
	public void updateStatus(String id, Board board) {
		if (!currentBoard.containsKey(id))
			throw new GameException("Game not founded.");
		currentBoard.put(id, board);
	}

	/**
	 * Returns current board of the game
	 * @param id input
	 * @return Board
	 */
	public Optional<Board> getBoard(String id) {
		if (currentBoard.containsKey(id))
			return Optional.of(currentBoard.get(id));
		throw new GameException("Game not founded.");
	}
	
	/**
	 * Create new board for the game
	 * @param id id for the board
	 * @param board new Board
	 */
	public void createBoard(String id, Board board){
		currentBoard.put(id, board);
	}

}
