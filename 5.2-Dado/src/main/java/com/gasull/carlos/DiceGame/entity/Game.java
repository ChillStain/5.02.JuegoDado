package com.gasull.carlos.DiceGame.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Games")
public class Game {

	@Id
	@Column(name = "Pk_GameId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idGame;

	@Column(name = "Dice_1")
	private int dice1;

	@Column(name = "Dice_2")
	private int dice2;

	@Column(name = "Victory")
	private boolean win;

	@JsonBackReference
	@JoinColumn(name = "Pk_PlayerId", nullable=false)
	@ManyToOne
	private Player player;
	

	public Game() {

	}
	
	public Game(Player player) {
		this.player = player;
		this.dice1 = playGame();
		this.dice2 = playGame();
		this.win = win(dice1, dice2);

	}

	public int playGame() {
		int gamePlay = (int) (Math.random() * 6 + 1);
		return gamePlay;
	}

	public boolean win(int dice1, int dice2) {
		boolean win = false;
		int result = dice1 + dice2;
		if (result == 7) {
			win = true;
		}
		return win;
	}


	

	@Override
	public String toString() {
		return "Game [idGame=" + idGame + ", dice1=" + dice1 + ", dice2=" + dice2 + ", win=" + win + ", player="
				+ player + "]";
	}
	

	public Long getIdGame() {
		return idGame;
	}

	public void setIdGame(Long idGame) {
		this.idGame = idGame;
	}

	public int getDice1() {
		return dice1;
	}

	public void setDice1(int dice1) {
		this.dice1 = dice1;
	}

	public int getDice2() {
		return dice2;
	}

	public void setDice2(int dice2) {
		this.dice2 = dice2;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
