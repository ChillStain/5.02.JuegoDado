package com.gasull.carlos.DiceGame.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gasull.carlos.DiceGame.entity.Player;
import com.gasull.carlos.DiceGame.repository.PlayerRepository;

@Service
@Transactional
public class PlayerService {

	@Autowired
	PlayerRepository playerRepository;

	public ArrayList<Player> getAllPlayers() {

		return (ArrayList<Player>) playerRepository.findAll();

	}

	public List<String> dataPlayers(List<Player> players) {

		List<String> dataPlayers = new ArrayList<String>();

		for (Player p : players) {

			dataPlayers.add(p.toString());
		}

		return dataPlayers;
	}

	public boolean playerExistsByName(Player player) {

		boolean playerExists = false;

		List<Player> allPlayers = getAllPlayers();

		int totalPlayers = allPlayers.size();

		if (player.getName() != null) {
			for (int i = 0; i < totalPlayers; i++) {

				if (player.getName().equalsIgnoreCase(allPlayers.get(i).getName())) {

					playerExists = true;
				}

			}
		} else {
			player.setName("Anonymous");
		}
		return playerExists;
	}

	public Player addPlayer(Player player) {

		playerRepository.save(player);

		return player;
	}

	public Player getPlayerById(Long id) throws Exception {

		Optional<Player> opPlayer = playerRepository.findById(id);

		return opPlayer.orElseThrow(() -> new Exception("No player with id " + id));

	}

	public Player updatePlayer(Player playerFound) {

		playerRepository.save(playerFound);

		return playerFound;
	}

	public void updatePercentage() {

		for (Player p : getAllPlayers()) {

			p.setVictoryPercentage(p.successPercentage(p.getGames()));
		}
	}

	public double getAverageRanking() {

		List<Player> players = playerRepository.findAll();

		return averagePercentagePlayers(players);
	}

	public double averagePercentagePlayers(List<Player> players) {

		double addPercentages = 0;
		double totalPlayers = players.size();

		for (Player p : players) {

			addPercentages += p.getVictoryPercentage();
		}

		double averagePercentage = addPercentages / totalPlayers;

		return averagePercentage;
	}

	public Player getWorstPlayer(List<Player> players) {

		//updatePercentage();
		players.sort(Comparator.comparing(Player::getVictoryPercentage));

		return players.get(0);
	}

	public Player getBestPlayer(List<Player> players) {

		//updatePercentage();
		players.sort(Comparator.comparing(Player::getVictoryPercentage).reversed());

		return players.get(0);
	}

	public boolean deletePlayer(Long id) {
		try {
			playerRepository.deleteById(id);
			return true;
		} catch (Exception err) {
			return false;
		}
	}

	/*
	 * public Optional<Player> getBestPlayer() {
	 * 
	 * List<Player> playerList = playerRepository.findAll();
	 * 
	 * if (playerList.isEmpty()) {
	 * 
	 * System.out.println("There are no players."); }
	 * 
	 * Collections.sort(playerList, new ComparatorPlayer());
	 * 
	 * System.out.println("The best player is: " + playerList.get(0).toString());
	 * 
	 * return Optional.of(playerList.get(0)); }
	 * 
	 * public Optional<Player> getWorstPlayer() {
	 * 
	 * List<Player> playerList = playerRepository.findAll();
	 * 
	 * if (playerList.isEmpty()) {
	 * 
	 * System.out.println("There are no players."); }
	 * 
	 * Collections.sort(playerList, new ComparatorPlayer());
	 * 
	 * System.out.println("The worst player is: " + playerList.get(playerList.size()
	 * - 1).toString());
	 * 
	 * return Optional.of(playerList.get(playerList.size() - 1)); }
	 * 
	 * public List<Player> getRanking() {
	 * 
	 * List<Player> playerList = playerRepository.findAll();
	 * 
	 * List<Player> playerRanking = new ArrayList<Player>();
	 * 
	 * if (playerList.isEmpty()) {
	 * 
	 * System.out.println("There are no players."); }
	 * 
	 * else {
	 * 
	 * Collections.sort(playerList, new ComparatorPlayer());
	 * 
	 * for (Player player : playerList) {
	 * 
	 * playerRanking.add(player);
	 * 
	 * System.out.println("Nombre: " + player.getName()); //
	 * System.out.println("Rate: " + player.getVictoryPercentage());
	 * 
	 * }
	 * 
	 * } return playerRanking; }
	 */

}
