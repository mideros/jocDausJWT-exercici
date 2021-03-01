package com.mideros.diceGameJWT.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mideros.diceGameJWT.dto.Player;
import com.mideros.diceGameJWT.exception.PlayerExistsException;
import com.mideros.diceGameJWT.exception.ResourceNotFoundException;
import com.mideros.diceGameJWT.security.JwtTokenUtil;
import com.mideros.diceGameJWT.service.IPlayerService;

@RestController
@RequestMapping("/v1")
public class PlayerController {

	@Autowired
	private IPlayerService playerService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	// Create player
	@PostMapping("/players/signup")
	public ResponseEntity<Object> addPlayerGame(@Valid @RequestBody Player player) {
		try {

			return ResponseEntity.ok().body(playerService.savePlayer(player));

		} catch (PlayerExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// Signing a player
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Player player) throws Exception {

		try {

			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(player.getUsername(), player.getPassword()));

			UserDetails userDetails = playerService.loadUserByUsername(player.getUsername());

			String token = jwtTokenUtil.generateToken(userDetails);

			return ResponseEntity.ok(" TOKEN: " + token);

		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	// Update player
	@PutMapping("/players/{id}")
	public ResponseEntity<Object> updatePlayerGame(@PathVariable(value = "id") int idPlayer,
			@Valid @RequestBody Player player) throws ResourceNotFoundException {

		try {
			if (playerService.getPlayerById(idPlayer) != null) {
				return ResponseEntity.ok().body(playerService.updatePlayer(idPlayer, player));
			} else {
				throw new PlayerExistsException("Player " + idPlayer + " does not exist!!!");
			}
		} catch (PlayerExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// get list all of Players
	@GetMapping("/players")
	public List<Player> listPlayer() {
		return playerService.listPlayers();
	}

	// Average ranking of all players
	@GetMapping("/players/ranking")
	public ResponseEntity<Object> ranking() {

		return new ResponseEntity<>("Average ranking of all players: " + playerService.averageRanking(), HttpStatus.OK);

	}

	// Ranking loser player
	@GetMapping("/players/ranking/loser")
	public ResponseEntity<Player> rankingLoserPlayer() {

		return new ResponseEntity<Player>(playerService.rankingLoser(), HttpStatus.OK);

	}

	// Ranking winner player
	@GetMapping("/players/ranking/winner")
	public ResponseEntity<Player> rankingWinnerPlayer() {

		return new ResponseEntity<Player>(playerService.rankingWinner(), HttpStatus.OK);

	}
}
