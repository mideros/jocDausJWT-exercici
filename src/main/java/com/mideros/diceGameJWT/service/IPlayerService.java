package com.mideros.diceGameJWT.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.mideros.diceGameJWT.dto.Player;

public interface IPlayerService {

	public List<Player> listPlayers();

	public Player savePlayer(Player player);

	public Player getPlayerById(int id_player);

	public Player updatePlayer(int id_player, Player player);

	public void deletePlayer(int id_player);

	public double averageRanking();

	public Player rankingLoser();

	public Player rankingWinner();

	public UserDetails loadUserByUsername(String username);

}