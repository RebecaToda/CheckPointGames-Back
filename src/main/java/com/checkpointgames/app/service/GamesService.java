package com.checkpointgames.app.service;

import com.checkpointgames.app.entity.GameKeys;
import com.checkpointgames.app.entity.Games;
import com.checkpointgames.app.exception.InvalidIdException;
import com.checkpointgames.app.repository.GamesKeysRepository;
import com.checkpointgames.app.repository.GamesRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GamesService {

    @Autowired
    private GamesRepository gamesRepository;

    @Autowired
    private GamesKeysRepository gamesKeysRepository; // Injetamos o repositório de chaves

    @Transactional // Garante que o Jogo e as Chaves sejam salvos juntos
    public Games saveGame(Games game){
        // 1. Salva o Jogo primeiro para ter o ID gerado
        Games savedGame = gamesRepository.save(game);

        // 2. Gera as chaves automaticamente baseado no Estoque (Inventory)
        if (savedGame.getInventory() != null && savedGame.getInventory() > 0) {
            for (int i = 0; i < savedGame.getInventory(); i++) {
                GameKeys key = new GameKeys();
                key.setIdGame(savedGame);
                key.setStatus(0); // 0 = Disponível
                // Gera uma chave única aleatória (ex: A1B2-C3D4-E5F6...)
                key.setKey(UUID.randomUUID().toString().toUpperCase());

                gamesKeysRepository.save(key);
            }
        }

        return savedGame;
    }

    public Games updateGames(Games games) {
        gamesRepository.findById(games.getId())
                .orElseThrow(() -> new InvalidIdException("Jogo não encontrado"));

        gamesRepository.updateGames(
                games.getName(),
                games.getDescription(),
                games.getCategory(),
                games.getInventory(),
                games.getValue(),
                games.getDiscount(),
                games.getStatus(),
                games.getLinkImage(),
                games.getId()
        );

        return gamesRepository.findById(games.getId())
                .orElseThrow(() -> new RuntimeException("Erro ao atualizar a senha"));
    }

    public List<Games> showGames(Games game) {
        return gamesRepository.findAll();
    }

    public List<Games> showActivityGames (Games game){
        return gamesRepository.findActivityGames();
    }

    public Optional<Games> findById (Integer id) {
        return gamesRepository.findById(id);
    }
}