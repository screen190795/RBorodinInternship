package com.game.service;

import com.game.Exceptions.EntityInsertException;
import com.game.controller.PlayerOrder;
import com.game.entity.Player;

import com.game.repository.PlayerRepository;
import com.game.utils.PlayerValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;

import static com.game.specifications.PlayerSpecification.*;


@Service
public class PlayerServiceImpl implements PlayerService {


    final
    PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @SuppressWarnings("ConstantConditions")
    @Override
    public Page<Player> getPlayersList(Map<String, String> params) {
        int pageNumber = 0;
        int pageSize = 3;
        String order = PlayerOrder.ID.getFieldName();

        if (params.containsKey("pageNumber")) {
            pageNumber = Integer.parseInt(params.get("pageNumber"));

        }

        if (params.containsKey("pageSize")) {
            pageSize = Integer.parseInt(params.get("pageSize"));

        }

        if (params.containsKey("order")) {
            order = PlayerOrder.valueOf(params.get("order")).getFieldName();
        }

        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by(order));

        if (params.isEmpty()) {
            return this.playerRepository.findAll(page);
        }

        if (!params.containsKey("name")) {
            params.put("name", null);
        }
        if (!params.containsKey("title")) {
            params.put("title", "");
        }
        if (!params.containsKey("birthdayAfter")) {
            params.put("birthdayAfter", null);
        }
        if (!params.containsKey("birthdayBefore")) {
            params.put("birthdayBefore", null);
        }
        if (!params.containsKey("experienceMin")) {
            params.put("experienceMin", null);
        }
        if (!params.containsKey("experienceMax")) {
            params.put("experienceMax", null);
        }
        if (!params.containsKey("race")) {
            params.put("race", null);
        }
        if (!params.containsKey("profession")) {
            params.put("profession", null);
        }
        return this.playerRepository.findAll(
                nameContains(params.get("name"))
                        .and(titleContains(params.get("title")))
                        .and(raceEqualTo(params.get("race")))
                        .and(birthdayBefore(params.get("before")))
                        .and(birthdayAfter(params.get("after")))
                        .and(professionEqualTo(params.get("profession")))
                        .and(experienceGreaterThenOrEqualTo(params.get("minExperience")))
                        .and(experienceLessThenOrEqualTo(params.get("maxExperience")))
                        .and(isBanned(params.get("banned")))
                        .and(levelLessThenOrEqualTo(params.get("maxLevel")))
                        .and(levelGreaterThenOrEqualTo(params.get("minLevel")))
                , page);

    }


    @Override
    public long getPlayersCount(Map<String, String> params) {
        return getPlayersList(params).getTotalElements();
    }

    @Override
    public Player createPlayer(Player player) {
        player.setLevel(Player.countLevel(player.getExperience()));
        player.setUntilNextLevel(Player.countUntilLevel(player.getExperience(), player.getLevel()));
        if (player.getBanned() == null) {
            player.setBanned(false);
        }
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player playerFromDb, Player updatedPlayer) throws EntityInsertException {
        if (playerFromDb.getName() != null && updatedPlayer.getName() != null) {
            if (!PlayerValidator.nameIsValid(updatedPlayer.getName())) {
                throw new EntityInsertException();
            }
            playerFromDb.setName(updatedPlayer.getName());
        }
        if (playerFromDb.getTitle() != null && updatedPlayer.getTitle() != null) {
            if (!PlayerValidator.titleIsValid(updatedPlayer.getTitle())) {
                throw new EntityInsertException();
            }
            playerFromDb.setTitle(updatedPlayer.getTitle());
        }
        if (playerFromDb.getRace() != null && updatedPlayer.getRace() != null) {
            playerFromDb.setRace(updatedPlayer.getRace());
        }
        if (playerFromDb.getProfession() != null && updatedPlayer.getProfession() != null) {
            playerFromDb.setProfession(updatedPlayer.getProfession());
        }
        if (playerFromDb.getBirthday() != null && updatedPlayer.getBirthday() != null) {
            if (!PlayerValidator.birthDayIsValid(updatedPlayer.getBirthday().getTime())) {
                throw new EntityInsertException();
            }
            playerFromDb.setBirthday(updatedPlayer.getBirthday());
        }
        if (playerFromDb.getBanned() != null && updatedPlayer.getBanned() != null) {
            playerFromDb.setBanned(updatedPlayer.getBanned());
        }
        if (playerFromDb.getExperience() != null && updatedPlayer.getExperience() != null) {
            if (!PlayerValidator.experienceIsValid(updatedPlayer.getExperience())) {
                throw new EntityInsertException();
            }
            playerFromDb.setExperience(updatedPlayer.getExperience());
            playerFromDb.setLevel(Player.countLevel(playerFromDb.getExperience()));
            playerFromDb.setUntilNextLevel((Player.countUntilLevel(playerFromDb.getExperience(), playerFromDb.getLevel())));

        }
        if (playerFromDb.getUntilNextLevel() != null && updatedPlayer.getUntilNextLevel() != null) {
            playerFromDb.setUntilNextLevel(updatedPlayer.getUntilNextLevel());
        }
        return playerRepository.save(playerFromDb);
    }

    @Override
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Optional<Player> getPlayer(Long id) {
        return playerRepository.findById(id);
    }

}
