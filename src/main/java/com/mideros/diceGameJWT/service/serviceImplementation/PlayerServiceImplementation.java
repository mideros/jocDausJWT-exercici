package com.mideros.diceGameJWT.service.serviceImplementation;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mideros.diceGameJWT.dto.Player;
import com.mideros.diceGameJWT.exception.PlayerExistsException;
import com.mideros.diceGameJWT.repository.IPlayerRepository;
import com.mideros.diceGameJWT.service.IPlayerService;

@Service
public class PlayerServiceImplementation implements IPlayerService, UserDetailsService {

	@Autowired
	IPlayerRepository playerRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public PlayerServiceImplementation(IPlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Override
	public List<Player> listPlayers() {
		return playerRepository.findAll();
	}

	@Override
	public Player savePlayer(Player player) {

		if (player.getName_player().isEmpty() || player.getName_player() == null) {
			player.setName_player("ANONYMOUS");
		}
		if (validPlayer(player)) {
			player.setPassword(bcryptEncoder.encode(player.getPassword()));
			return playerRepository.save(player);
		} else {
			throw new PlayerExistsException("The player's name " + player.getName_player() + " or username "
					+ player.getUsername() + " is already registered.");
		}
	}

	@Override
	public Player getPlayerById(int id_player) {
		return playerRepository.findById(id_player);
	}

	@Override
	public Player updatePlayer(int id_player, Player player) {

		Player oldDataPlayer = new Player();
		oldDataPlayer = playerRepository.findById(id_player);

		if (player.getName_player().isEmpty() || player.getName_player() == null) {
			player.setName_player("ANONYMOUS");
		}
		if (validUpdatePlayer(id_player, player)) {

			oldDataPlayer.setName_player(player.getName_player());
			oldDataPlayer.setPassword(bcryptEncoder.encode(player.getPassword()));

			return playerRepository.save(oldDataPlayer);

		} else {
			throw new PlayerExistsException(
					"The username " + player.getName_player() + " or password to update is not valid!");
		}
	}

	@Override
	public void deletePlayer(int id_player) {
		playerRepository.deleteById(id_player);

	}

	@Override
	public Player rankingLoser() {
		List<Player> allPlayers = playerRepository.findAll();
		allPlayers.sort(Comparator.comparing(Player::getSuccess_rate_player));
		return allPlayers.get(0);
	}

	@Override
	public Player rankingWinner() {
		List<Player> allPlayersW = playerRepository.findAll();
		allPlayersW.sort(Comparator.comparing(Player::getSuccess_rate_player).reversed());
		return allPlayersW.get(0);
	}

	@Override
	public double averageRanking() {

		List<Player> allPlayersR = playerRepository.findAll();
		Double averageRanking = allPlayersR.stream().mapToDouble(Player::getSuccess_rate_player).average().orElse(0.0);
		return averageRanking;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Player player = playerRepository.findByUsername(username);
		if (player == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new User(player.getUsername(), player.getPassword(), new ArrayList<>());
	}

	public boolean validPlayer(Player player) throws PlayerExistsException {
		List<Player> listP = playerRepository.findAll();
		boolean validP = false;

		if ((player.getUsername().length() >= 3) && (player.getPassword().length() == 8)
				&& (player.getName_player().length() >= 3)) {

			if (listP.contains(player)) {
				return false;
			} else {
				for (Player p : listP) {
					if (p.getName_player().equalsIgnoreCase(player.getName_player())) {
						return false;
					} else {
						if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
							return false;
						} else {
							validP = true;
						}
					}
				}
			}
		} else {
			throw new PlayerExistsException("The name " + player.getName_player() + " or username "
					+ player.getUsername() + " is less than 3 characters or password is different than 8 characters!");
		}
		return validP;
	}

	public boolean validUpdatePlayer(int id_player, Player player) throws PlayerExistsException {

		List<Player> listU = playerRepository.findAll();
		boolean validU = false;

		if ((player.getPassword().length() == 8) && (player.getName_player().length() >= 3)) {

			if (player.getName_player().equalsIgnoreCase("ANONYMOUS")) {
				validU = true;
			} else {
				for (Player p : listU) {

					if (player.getName_player().equalsIgnoreCase(p.getName_player())) {
						return false;
					} else {
						if (player.getName_player().equalsIgnoreCase(p.getUsername())) {
							return false;
						} else {
							validU = true;
						}
					}
				}
			}
		} else {
			throw new PlayerExistsException("The name " + player.getName_player()
					+ " is less than 3 characters or password is different than 8 characters!");
		}
		return validU;
	}
}