package backbase.quiz.solution.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import backbase.quiz.solution.model.CurrentGame;
import backbase.quiz.solution.model.Game;
import backbase.quiz.solution.service.GameService;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	@Mock
	private GameService gameService;
	
	@InjectMocks
	private GameController GameControllerTest;
	
	@Test
	public void testCreateNewGame(){
		when(gameService.createGame()).thenReturn(Game.builder().id("TEST").uri("URI").build());
		ResponseEntity<?> response = GameControllerTest.createNewGame();
		assertEquals("TEST", ((Game)response.getBody()).getId());
		assertEquals("URI", ((Game)response.getBody()).getUri());
	}
	
	@Test
	public void testPlay(){
		when(gameService.play(anyString(), anyInt())).thenReturn(CurrentGame.builder().id("ID").uri("URI").build());
		ResponseEntity<?> response = GameControllerTest.play("TEST", 1);
		assertEquals("ID", ((CurrentGame)response.getBody()).getId());
		assertEquals("URI", ((CurrentGame)response.getBody()).getUri());
	}
}
