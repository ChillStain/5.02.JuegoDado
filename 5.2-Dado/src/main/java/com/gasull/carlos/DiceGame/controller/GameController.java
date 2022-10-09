package com.gasull.carlos.DiceGame.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gasull.carlos.DiceGame.entity.Game;
import com.gasull.carlos.DiceGame.entity.Player;
import com.gasull.carlos.DiceGame.service.GameService;
import com.gasull.carlos.DiceGame.service.PlayerService;

@RestController
@RequestMapping
public class GameController {

	@Autowired
	GameService gameService;

	@Autowired
	PlayerService playerService;

	@GetMapping("/players/{id}/games")
	public ResponseEntity<?> getGamesByPlayer (@PathVariable("id") Long idPlayer) throws Exception {
        Player player = playerService.getPlayerById(idPlayer);
		return ResponseEntity.status(HttpStatus.OK).body(gameService.getGamesByPlayer(player));
	}
	
	@DeleteMapping("/players/{id}/games")
	public ResponseEntity<?> deleteGames (@PathVariable("id")Long idPlayer) throws Exception{
		
		Player player = playerService.getPlayerById(idPlayer);
		
		gameService.deleteGames(player);
		
		return ResponseEntity.status(HttpStatus.OK).body("The games have been deleted.");
	}

	@PostMapping("/players/{id}/games")
	 public ResponseEntity<?> addGame (@PathVariable(value="id") Long id) throws Exception {
	      Player player= playerService.getPlayerById(id);
	       //gameService.addGame(player);
	       return ResponseEntity.status(HttpStatus.CREATED).body(gameService.addGame(player));
	   }

	/*
	 * @PostMapping("/{id}") public ResponseEntity<?> playGame(@PathVariable("id")
	 * Long id) {
	 * 
	 * Optional<Player> opPlayer = playerService.getPlayerById(id);
	 * 
	 * if (opPlayer.isPresent()) {
	 * 
	 * Player player = opPlayer.get();
	 * 
	 * Game game = new Game(player);
	 * 
	 * player.addGame(game);
	 * 
	 * return ResponseEntity.ok(game); }
	 * 
	 * else { return ResponseEntity.notFound().build(); }
	 * 
	 * }
	 */

	/*@GetMapping("/{id}")
	public ResponseEntity<?> getGamesByPlayerId(@PathVariable(value = "idPlayer") Long id) {

		List<Game> games = StreamSupport.stream(gameService.getGamesByPlayerId(id).spliterator(), false)
				.collect(Collectors.toList());

		return ResponseEntity.ok(games);

	}*/
}
