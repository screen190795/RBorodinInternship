package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Player} class
 */
public interface PlayerRepository extends CrudRepository<Player, Long>, JpaSpecificationExecutor<Player> {

    Optional<Player> findById(Long id);

    List<Player> findAll();


    Page<Player> findAll(Pageable page);
}
