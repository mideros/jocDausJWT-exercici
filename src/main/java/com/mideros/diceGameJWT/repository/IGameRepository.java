package com.mideros.diceGameJWT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mideros.diceGameJWT.dto.Game;
import com.mideros.diceGameJWT.dto.Player;

@Repository
@Transactional
public interface IGameRepository extends JpaRepository<Game, Integer> {

	public List<Game> getGameByPlayer(Player player);

	public void deleteGameByPlayer(Player player);
}
