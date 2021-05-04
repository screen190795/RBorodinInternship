package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;

import static com.game.specifications.PlayerSpecification.*;


@Service
public class PlayerServiceImpl implements PlayerService {


    @Autowired
    PlayerRepository playerRepository;


    @Override
    public Page<Player> getPlayersList(Map<String, String> params) {
        int pageNumber = 0;
        int pageSize = 3;
        String order = PlayerOrder.ID.getFieldName();
        System.out.println(order);

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
        if (params.containsKey("pageNumber")) {

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
                , page);

    }


    @Override
    public long getPlayersCount(Map<String, String> params) {
        return getPlayersList(params).getTotalElements();
    }

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
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
