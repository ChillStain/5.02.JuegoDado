package com.gasull.carlos.DiceGame.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Player")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Pk_PlayerId")
	private Long idPlayer;

	@Column(name="name")
	private String name;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="victory_percentage")
	private double victoryPercentage;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "player")
	private List<Game> games;


	private String role;


	private String password;
	

	public Player() {

	}

	public Player(String name, String password) {
		this.name = name;

		this.date = new Date();

		this.password = password;

		this.role = "User";

		this.games = null;

	}

	public double successPercentage(List<Game> games) {
		double totalGames = games.size();
		if (totalGames == 0) return 0;
		double victorys = 0;


		for (int i = 0; i < totalGames; i++) {
			if (games.get(i).isWin()) {
				victorys += 1;
			}
		}

		double percentatge = victorys / totalGames * 100;

		return percentatge;
	}
	
	

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
		games.forEach(game -> game.setPlayer(this));
	}

	public void addGame(Game game) {

		games.add(game);
	}

	public Long getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(Long idPlayer) {
		this.idPlayer = idPlayer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getVictoryPercentage() {
		return victoryPercentage;
	}

	public void setVictoryPercentage(double victoryPercentage) {
		this.victoryPercentage = victoryPercentage;
	}

	@Override
	public String toString() {
		return "name='" + name + '\'' +
				", victoryPercentage=" + victoryPercentage +
				'}';
	}

	public String getRole() {
		return role;
	}

	public String getPassword() {
		return password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
