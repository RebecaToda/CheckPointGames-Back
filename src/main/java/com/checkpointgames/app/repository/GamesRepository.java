package com.checkpointgames.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.checkpointgames.app.entity.Games;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface GamesRepository extends JpaRepository<Games, Integer>, GamesRepositoryCustom{

    Optional<Games> findById(Integer id);
    
    @Query(value = "SELECT * FROM games where status = '0'", nativeQuery = true)
    List<Games> findActivityGames();

}
