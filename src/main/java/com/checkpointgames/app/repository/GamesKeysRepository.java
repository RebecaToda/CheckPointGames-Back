package com.checkpointgames.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.checkpointgames.app.entity.GameKeys;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface GamesKeysRepository extends JpaRepository<GameKeys, Integer>, GamesKeysRepositoryCustom{

    @Override
    Optional<GameKeys> findById(Integer id);
    
    @Query(value = "SELECT * FROM GameKeys where status = '0'", nativeQuery = true)
    List<GameKeys> findActivityGamesKeys();
    
    List<GameKeys> findByIdGame_Id(Integer idGame);
    
    @Query("SELECT k FROM GameKeys k WHERE k.idGame.id = :gameId AND k.status = 0 ORDER BY k.id ASC")
    List<GameKeys> findAvailableKeys(Integer gameId);

}
