package com.game.service;

import com.game.Exceptions.EntityInsertException;
import com.game.entity.Player;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Optional;


public interface PlayerService {


    Page<Player> getPlayersList(Map<String, String> params);

    long getPlayersCount(Map<String, String> params);

    Player createPlayer(Player player);

    Player updatePlayer(Player playerFromDb, Player updatedPlayer) throws EntityInsertException;

    void deletePlayer(Long id);

    Optional<Player> getPlayer(Long id);

}
