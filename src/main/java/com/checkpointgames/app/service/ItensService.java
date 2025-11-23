package com.checkpointgames.app.service;

import com.checkpointgames.app.dto.ItensUpdateDTO;
import com.checkpointgames.app.entity.Itens;
import com.checkpointgames.app.entity.GameKeys;
import com.checkpointgames.app.entity.Games;
import com.checkpointgames.app.repository.ItensRepository;
import com.checkpointgames.app.repository.GamesKeysRepository;
import com.checkpointgames.app.repository.GamesRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItensService {

    @Autowired
    private ItensRepository itensRepository;

    @Autowired
    private GamesKeysRepository gameKeysRepository;

    @Autowired
    private GamesRepository gamesRepository;

   public Itens saveItens(Itens itens) {

        Integer gameId = itens.getIdGame().getId();


        List<GameKeys> keys = gameKeysRepository.findAvailableKeys(gameId);

        if (keys.isEmpty()) {
            throw new RuntimeException("Nenhuma chave disponível para este game!");
        }

        GameKeys key = keys.get(0);

        key.setStatus(1);
        gameKeysRepository.save(key);


        itens.setIdKey(key);


        Games game = gamesRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game não encontrado"));

        gamesRepository.save(game);

        return itensRepository.save(itens);
    }



    public Itens updateItem(ItensUpdateDTO dto) {

        Itens existing = itensRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado!"));

        itensRepository.updateItens(
                existing.getIdGame().getId(),   // mantém o game
                dto.getIdOrder(),               // atualiza pedido
                existing.getIdKey().getId(),    // mantém key correta
                dto.getDiscount(),
                dto.getAddition(),
                dto.getValue(),
                existing.getId()
        );

        return itensRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Erro ao atualizar item!"));
    }

    public List<Itens> findByOrder(Integer orderId) {
        return itensRepository.findByIdOrder_Id(orderId);
    }

    public List<Itens> findByGame(Integer gameId) {
        return itensRepository.findByIdGame_Id(gameId);
    }

    public List<Itens> findAll() {
        return itensRepository.findAll();
    }

    public Optional<Itens> findById(Integer id) {
        return itensRepository.findById(id);
    }
}
