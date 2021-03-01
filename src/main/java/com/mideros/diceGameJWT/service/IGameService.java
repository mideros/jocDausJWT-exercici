package com.mideros.diceGameJWT.service;

import java.util.List;

import com.mideros.diceGameJWT.dto.Game;
import com.mideros.diceGameJWT.dto.Player;

public interface IGameService {

	public List<Game> listGames();

	public Game saveGame(Game game);

	public Game getGameById(int id_game);

	public Game updateGame(Game game);

	public void deleteGame(int id_game);

	public Game rollDice(int id_player, Player player);

	public List<Game> getGameByPlayer(Player player);

	public String deleteGameByPlayer(Player player);

}
