package backbase.quiz.solution.repository;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import backbase.quiz.solution.model.Board;
import backbase.quiz.solution.model.Game;

@RunWith(MockitoJUnitRunner.class)
public class BoardRepositoryTest {

	public Map<String, Board> currentBoard = new HashMap<String, Board>();

	@InjectMocks
	private BoardRepository boardRepository;

	@Before
	public void createRepo() {
		currentBoard.put("1", new Board(Game.builder().id("123").build()));
		currentBoard.put("2", new Board(Game.builder().id("XYZ").build()));
		boardRepository.currentBoard = currentBoard;
	}

	@Test
	public void testCreateBoard() {
		boardRepository.createBoard("X", new Board(Game.builder().id("XYZ").build()));
	}

	@Test
	public void testGetBoard() {
		Optional<Board> board = boardRepository.getBoard("1");
		assertEquals("123", board.get().getGame().getId());

		Optional<Board> board2 = boardRepository.getBoard("2");
		assertEquals("XYZ", board2.get().getGame().getId());
	}

	@Test
	public void testUpdateStatus() {
		boardRepository.updateStatus("1", new Board(Game.builder().id("XYZ").build()));
		assertEquals("XYZ", currentBoard.get("1").getGame().getId());
	}

}
