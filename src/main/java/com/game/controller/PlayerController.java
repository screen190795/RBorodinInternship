package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rest/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player player = this.playerService.getPlayer(id).orElse(null);

        if (id == 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (player == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }


        System.out.println(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> createPlayer(Player player) {
        HttpHeaders headers = new HttpHeaders();


        if (player == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.playerService.createPlayer(player);
        return new ResponseEntity<>(player, headers, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> updatePlayer(@RequestBody @Validated Player player) {
        HttpHeaders headers = new HttpHeaders();

        if (player == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.playerService.createPlayer(player);

        return new ResponseEntity<>(player, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") long id) {
        Optional<Player> player = this.playerService.getPlayer(id);

        if (!player.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.playerService.deletePlayer(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "")
    @ResponseBody
    public List<Player> getPlayersList(@RequestParam Map<String, String> params) {

        System.out.println(params);

        List<Player> playersList = this.playerService.getPlayersList(params).getContent();

        try {
            if (playersList.isEmpty()) {
                return null;
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
