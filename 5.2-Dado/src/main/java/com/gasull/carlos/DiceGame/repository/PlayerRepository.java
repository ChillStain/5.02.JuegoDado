package com.gasull.carlos.DiceGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.gasull.carlos.DiceGame.entity.Player;

import java.util.Optional;


public interface PlayerRepository extends JpaRepository<Player, Long>{

    Optional<Player> findByName(String username);
}
