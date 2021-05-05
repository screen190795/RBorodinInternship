package com.game.controller;

import com.game.Exceptions.EntityInsertException;
import com.game.entity.Player;
import com.game.service.PlayerService;
import com.game.utils.PlayerValidator;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Long id) {

        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (id == 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Player player = this.playerService.getPlayer(id).orElse(null);

        if (player == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {

        if (!PlayerValidator.enoughParamsToCreatePlayer(player)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            if (!PlayerValidator.nameIsValid(player.getName())
                    || !PlayerValidator.titleIsValid(player.getTitle())
                    || !PlayerValidator.experienceIsValid(player.getExperience())
                    || !PlayerValidator.birthDayIsValid(player.getBirthday().getTime())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return new ResponseEntity<>(playerService.createPlayer(player), HttpStatus.OK);

    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") String id, @RequestBody(required = false) Player updatedPlayer) {


        if (!PlayerValidator.idIsValid(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Player playerFromDb = playerService.getPlayer(Long.parseLong(id)).orElse(null);
        if (playerFromDb == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            if (updatedPlayer.checkNull()) {
                return new ResponseEntity<>(playerFromDb, HttpStatus.OK);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            playerFromDb = playerService.updatePlayer(playerFromDb, updatedPlayer);
        } catch (EntityInsertException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(playerFromDb, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") long id) {
        Optional<Player> player = this.playerService.getPlayer(id);

        if (id == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!player.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.playerService.deletePlayer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Player> getPlayersList(@RequestParam Map<String, String> params) {

        List<Player> playersList = this.playerService.getPlayersList(params).getContent();

        try {
            if (playersList.isEmpty()) {
                return playersList;
            }
            return playersList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/count")
    @ResponseBody
    public ResponseEntity<Long> getPlayersCount(@RequestParam Map<String, String> params) {
        if (params.isEmpty()) {
            return new ResponseEntity<>(playerService.getPlayersList(params).getTotalElements(), HttpStatus.OK);

        }

        if (params.size() < 4) {
            return new ResponseEntity<>(playerService.getPlayersList(params).getTotalElements(), HttpStatus.OK);
        }

        long count = this.playerService.getPlayersCount(params);

        return new ResponseEntity<>(count, HttpStatus.OK);
    }


}
