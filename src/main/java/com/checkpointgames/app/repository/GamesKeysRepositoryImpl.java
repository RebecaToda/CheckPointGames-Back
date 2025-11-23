package com.checkpointgames.app.repository;

import com.checkpointgames.app.entity.Games;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class GamesKeysRepositoryImpl {
    
    @PersistenceContext
    private EntityManager entityManager;    
    
    private Games game;
    

    public void updateGamesKeys(Integer id, Integer idGame, String key, Integer status) {

        entityManager.createQuery("""
            UPDATE GameKeys g 
               SET g.idGame.id = :idGame, 
                   g.key = :key, 
                   g.status = :status 
             WHERE g.id = :id
        """)
        .setParameter("idGame", idGame)
        .setParameter("key", key)
        .setParameter("status", status)
        .setParameter("id", id)
        .executeUpdate();
    }
}