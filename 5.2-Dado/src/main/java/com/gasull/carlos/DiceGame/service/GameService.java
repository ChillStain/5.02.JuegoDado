package com.gasull.carlos.DiceGame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gasull.carlos.DiceGame.entity.Game;
import com.gasull.carlos.DiceGame.entity.Player;
import com.gasull.carlos.DiceGame.repository.GameRepository;
import com.gasull.carlos.DiceGame.repository.PlayerRepository;

@Service
public class GameService {

	@Autowired
	GameRepository gameRepository;

	@Autowired
	PlayerRepository playerRepository;

	public Game addGame(Player player) {

		Game game = new Game(player);
		gameRepository.save(game);
		player.setVictoryPercentage(player.successPercentage(player.getGames()));
		playerRepository.save(player);

		return game;
	}

	public void deleteGames(Player player) {

		gameRepository.deleteAll(player.getGames());
	}

	public List<String> dataGamesOfPlayer(List<Game> games) {

		List<String> dataGamesOfPlayer = new ArrayList<String>();

		for (Game g : games) {

			dataGamesOfPlayer.add(g.toString());
		}

		return dataGamesOfPlayer;
	}

	public List<Game> getGamesByPlayer(Player player) {

		return gameRepository.findAllByPlayer(player);
	}

	public List<Game> getGamesByPlayerId(Long id) {

		Optional<Player> opPlayer = playerRepository.findById(id);

		Player player = opPlayer.get();

		return player.getGames();

	}

}
