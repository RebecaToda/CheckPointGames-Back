package com.checkpointgames.app.repository;


public interface GamesKeysRepositoryCustom {
    void updateGamesKeys(Integer id, Integer idGame, String key, Integer status);
}
