package backbase.quiz.solution.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import backbase.quiz.solution.exception.GameException;
import backbase.quiz.solution.model.Board;
import backbase.quiz.solution.model.CurrentGame;
import backbase.quiz.solution.model.Game;
import backbase.quiz.solution.repository.BoardRepository;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
	
	@Mock
	private BoardRepository boardRepository;
	
	@InjectMocks
	GameService gameService;
	
	@Before
	public void setup(){
		ReflectionTestUtils.setField(gameService, "uri", "http://foo/{0}");
	}
	
	@Test
	public void testCreateGame(){
		doNothing().when(boardRepository).createBoard(anyString(), any(Board.class));
		Game game = gameService.createGame();
		assertNotNull(game.getId());
		assertNotNull(game.getUri());
	}
	
	@Test
	public void tesPlay(){
		doNothing().when(boardRepository).updateStatus(anyString(), any());
		Board board = new Board(Game.builder().id("ID").build());
		Optional<Board> userOptional = Optional.of(board);
		when(boardRepository.getBoard("GAMEID")).thenReturn(userOptional);
		CurrentGame currentGame = gameService.play("GAMEID", 1);
		assertEquals("GAMEID", currentGame.getId());
		assertEquals(String.valueOf(7), currentGame.getStatus().get(2));
	}
	
	@Test(expected = GameException.class)
	public void testInvalidPitId(){
		CurrentGame currentGame = gameService.play("GAMEID", 7);
	}

}
