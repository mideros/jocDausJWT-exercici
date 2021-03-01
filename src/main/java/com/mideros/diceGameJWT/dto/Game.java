package com.mideros.diceGameJWT.dto;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "game")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_game", unique = true)
	private int id_game;

	@Column(name = "dice_one")
	@NotNull
	@Min(value = 1, message = "the minimun dice value 1")
	@Max(value = 6, message = "the maxim dice value 6")
	@Positive(message = "the dice value should be a positive number")
	private int dice_one;

	@Column(name = "dice_two")
	@NotNull
	@Min(value = 1, message = "the minimun dice value 1")
	@Max(value = 6, message = "the maxim dice value 6")
	@Positive(message = "the dice value should be a positive number")
	private int dice_two;

	@Column(name = "win_game", columnDefinition = "tinyint default false")
	private boolean win_game;

	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;

	public Game() {

		this.dice_one = generateDiceValue();
		this.dice_two = generateDiceValue();
		this.win_game = checkDiceRoll(dice_one, dice_two);

	}

	public Game(int id_game, Player player) {

		this.id_game = id_game;
		this.dice_one = generateDiceValue();
		this.dice_two = generateDiceValue();
		this.win_game = checkDiceRoll(dice_one, dice_two);
		this.player = player;
	}

	public int getId_game() {
		return id_game;
	}

	public void setId_game(int id_game) {
		this.id_game = id_game;
	}

	public int getDice_one() {
		return dice_one;
	}

	public void setDice_one(int dice_one) {
		this.dice_one = dice_one;
	}

	public int getDice_two() {
		return dice_two;
	}

	public void setDice_two(int dice_two) {
		this.dice_two = dice_two;
	}

	public boolean isWin_game() {
		return win_game;
	}

	public void setWin_game(boolean win_game) {
		this.win_game = win_game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int generateDiceValue() {

		int diceValue = (int) Math.floor(Math.random() * 6 + 1);

		return diceValue;
	}

	public boolean checkDiceRoll(int dice_one, int dice_two) {

		boolean checkDice = (dice_one + dice_two) == 7 ? true : false;

		return checkDice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_game;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (id_game != other.id_game)
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [id_game=" + id_game + ", dice_one=" + dice_one + ", dice_two=" + dice_two + ", win_game="
				+ win_game + ", player=" + player + "]";
	}
}
