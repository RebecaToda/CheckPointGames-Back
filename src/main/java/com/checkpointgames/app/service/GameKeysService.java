package com.checkpointgames.app.service;

import com.checkpointgames.app.entity.GameKeys;
import com.checkpointgames.app.entity.Games;
import com.checkpointgames.app.exception.InvalidIdException;
import com.checkpointgames.app.repository.GamesKeysRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameKeysService {
    
       
    @Autowired
    private GamesKeysRepository gamesKeysRepository;

    public GameKeys saveGame(GameKeys game){
        return gamesKeysRepository.save(game);
    }
    
    public GameKeys updateGameKeys(GameKeys games) {
        gamesKeysRepository.findById(games.getId())
            .orElseThrow(() -> new InvalidIdException("Jogo não encontrado"));
        
        gamesKeysRepository.updateGamesKeys(games.getId(),games.getIdGame().getId(), games.getKey(), games.getStatus());
        
        return gamesKeysRepository.findById(games.getId())
            .orElseThrow(() -> new RuntimeException("Erro ao atualizar a senha"));
    }    
    
    public List<GameKeys> showGameKeys(GameKeys game) {
        return gamesKeysRepository.findAll();
    }
    
    public List<GameKeys> showActivityGameKeys (GameKeys game){
        return gamesKeysRepository.findActivityGamesKeys();
    }
    
    public List<GameKeys> findKeysByGame(Integer gameId) {
       return gamesKeysRepository.findByIdGame_Id(gameId);
    }   
    
}
