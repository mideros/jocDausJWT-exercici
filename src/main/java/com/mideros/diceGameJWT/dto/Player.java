package com.mideros.diceGameJWT.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "player")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_player", unique = true)
	private int id_player;

	@Column(name = "name_player", length = 150, columnDefinition = "default Anonymous")
	@Size(max = 150, message = "Name  must be 150 characters")
	private String name_player;

	@Column(name = "username")
	@Size(max = 150, message = "the username must be greater than 3 and less than 20 characters")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "date_register_player")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_register_player;

	@Column(name = "success_rate_player")
	private double success_rate_player;

	@OneToMany
	@JoinColumn(name = "player_id")
	private List<Game> gameList;

	public Player() {
	}

	public Player(String name_player) {

		this.name_player = name_player;
	}

	public Player(int id_player, String name_player, String username, String password, Date date_register_player,
			double success_rate_player, List<Game> gameList) {

		this.id_player = id_player;
		this.name_player = name_player;
		this.username = username;
		this.password = password;
		this.date_register_player = date_register_player;
		this.success_rate_player = success_rate_player;
		this.gameList = gameList;
	}

	public int getId_player() {
		return id_player;
	}

	public void setId_player(int id_player) {
		this.id_player = id_player;
	}

	public String getName_player() {
		return name_player;
	}

	public void setName_player(String name_player) {
		this.name_player = name_player;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDate_register_player() {
		return date_register_player;
	}

	public void setDate_register_player(Date date_register_player) {
		this.date_register_player = date_register_player;
	}

	public double getSuccess_rate_player() {
		return success_rate_player;
	}

	public void setSuccess_rate_player(double success_rate_player) {
		this.success_rate_player = success_rate_player;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "Game")
	public List<Game> getGameList() {
		return gameList;
	}

	public void setGameList(List<Game> gameList) {
		this.gameList = gameList;
	}

	public void updateSuccessRate() {

		double sR = 0;

		for (Game pl : this.gameList) {
			if (pl.isWin_game()) {
				sR++;
			}
		}
		this.setSuccess_rate_player((sR / this.gameList.size()) * 100);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name_player == null) ? 0 : name_player.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Player other = (Player) obj;
		if (name_player == null) {
			if (other.name_player != null)
				return false;
		} else if (!name_player.equals(other.name_player))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [id_player=" + id_player + ", name_player=" + name_player + ", username_player=" + username
				+ ", date_register_player=" + date_register_player + ", success_rate_player=" + success_rate_player
				+ ", gameList=" + gameList + "]";
	}

}