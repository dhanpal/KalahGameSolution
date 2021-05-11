package backbase.quiz.solution.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import backbase.quiz.solution.service.GameService;

@RestController
public class GameController {
	
	@Autowired
	private final GameService gameService;
	
	public GameController(GameService gameService){
		this.gameService = gameService;
	}
	
	/**
	 * End point for creating new game
	 * @return gameId and uri to the game
	 */
	@RequestMapping(value = "/games", method = RequestMethod.POST, produces="application/json", consumes="application/json")
	public ResponseEntity<?> createNewGame(){
		return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame());
	}
	
	/**
	 * End point to make a move in given game
	 * @param gameId gameId
	 * @param pitId pitId
	 * @return gameId, uri and updated status of game board
	 */
	@RequestMapping(value = "/games/{gameId}/pits/{pitId}", method = RequestMethod.PUT, produces="application/json", consumes="application/json")
	public ResponseEntity<?> play(@PathVariable("gameId") String gameId, @PathVariable("pitId") Integer pitId){
		return ResponseEntity.status(HttpStatus.OK).body(gameService.play(gameId, pitId));
	}

}
