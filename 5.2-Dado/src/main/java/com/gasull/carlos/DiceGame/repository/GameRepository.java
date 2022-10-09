package com.gasull.carlos.DiceGame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gasull.carlos.DiceGame.entity.Game;
import com.gasull.carlos.DiceGame.entity.Player;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

	
	List<Game> findAllByPlayer(Player player);
}
