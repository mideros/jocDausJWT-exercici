package com.mideros.diceGameJWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mideros.diceGameJWT.dto.Player;

@Repository
@Transactional
public interface IPlayerRepository extends JpaRepository<Player, Integer> {

	public Player findById(int id_player);

	public Player findByUsername(String username);
}
