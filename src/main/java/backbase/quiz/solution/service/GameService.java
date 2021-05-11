package backbase.quiz.solution.service;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import backbase.quiz.solution.exception.GameException;
import backbase.quiz.solution.model.Board;
import backbase.quiz.solution.model.CurrentGame;
import backbase.quiz.solution.model.Game;
import backbase.quiz.solution.repository.BoardRepository;
import backbase.quiz.solution.utils.Constants;

@Service
public class GameService {

	@Value("${uri}")
	private String uri;

	@Autowired
	BoardRepository boardRepository;

	/**
	 * Creates a new game and returns the game id and url for the game
	 * @return Create game
	 */
	public Game createGame() {
		String gameId = UUID.randomUUID().toString();
		Game game = Game.builder().id(gameId).uri(MessageFormat.format(uri, gameId)).build();
		Board board = new Board(game);
		boardRepository.createBoard(gameId, board);
		return game;
	}

	/**
	 * update the status of current game board and returns the updated board
	 * @param gameId current game Id
	 * @param pitId pitId which to move
	 * @return Statuc of Current Game
	 */
	public CurrentGame play(String gameId, Integer pitId) {
		if(Constants.PAYER_1_KALAH.equals(pitId) || Constants.PAYER_2_KALAH.equals(pitId) || pitId < 0 || pitId > 14){
			throw new GameException("Invalid pit");
		}
		
		Board board = boardRepository.getBoard(gameId).orElseThrow(() -> new GameException("Invalid game id"));
		
		board.move(pitId);
		boardRepository.updateStatus(gameId, board);
		return CurrentGame.builder().id(gameId).uri(MessageFormat.format(uri, gameId))
				.status(board.getMyBoard().entrySet().stream().collect(
						Collectors.toMap(entry -> entry.getKey(), entry -> String.valueOf((entry.getValue())))))
				.build();
	}

}
