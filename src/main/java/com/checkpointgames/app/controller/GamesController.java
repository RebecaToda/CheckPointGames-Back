package com.checkpointgames.app.controller;

import com.checkpointgames.app.entity.Games;
import com.checkpointgames.app.service.GamesService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/games")
public class GamesController {

    @Autowired
    private GamesService gamesService;

    // Ajustado para /create para bater com o Frontend
    @PostMapping("/create")
    public Games saveGame(@Valid @RequestBody Games game){
        return gamesService.saveGame(game);
    }

    // Ajustado para /update/{id} com PUT para bater com o Frontend
    @PutMapping("/update/{id}")
    public ResponseEntity<Games> updateUser(@PathVariable Integer id, @Valid @RequestBody Games game){
        game.setId(id); // Garante que o ID do objeto é o mesmo da URL
        return ResponseEntity.ok(gamesService.updateGames(game));
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Games> updateStatus(@PathVariable Integer id, @RequestBody Games statusData){
        // Busca o jogo atual
        Games game = gamesService.findById(id).orElseThrow();
        // Atualiza só o status
        game.setStatus(statusData.getStatus());
        // Salva
        return ResponseEntity.ok(gamesService.updateGames(game));
    }

    @GetMapping("/showGames")
    public List<Games> showGames(Games game){
        return gamesService.showGames(game);
    }

    @GetMapping("/showActivityGames")
    public List<Games> showActivityGames (Games game){
        return gamesService.showActivityGames(game);
    }

    @GetMapping("/showGamesById/{id}")
    public ResponseEntity<Games> showGamesById (@PathVariable Integer id){
        return gamesService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Integer id) {

        Games game = gamesService.findById(id).orElseThrow();
        game.setStatus(1);
        gamesService.updateGames(game);
        return ResponseEntity.ok().build();
    }
}