
package com.checkpointgames.app.controller;

import com.checkpointgames.app.entity.Users;
import com.checkpointgames.app.entity.GameKeys;
import com.checkpointgames.app.service.GameKeysService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gamekeys")
public class GamesKeysController {
    
    @Autowired
    private GameKeysService gamesKeysService;
    
    @PostMapping("/createGameKeys")
    public GameKeys saveGame(@Valid @RequestBody GameKeys game){
        return gamesKeysService.saveGame(game);
    }
    
    @PostMapping("/updateGameKeys")
    public GameKeys updateUser(@Valid @RequestBody GameKeys game){
        return gamesKeysService.updateGameKeys(game);
    }

    @GetMapping("/showGameKeys")
    public List<GameKeys> showGameKeys(GameKeys game){
        return gamesKeysService.showGameKeys(game);
    }
    
    @GetMapping("/showByGame/{idGame}")
    public List<GameKeys> showByGame(@PathVariable Integer idGame) {
        return gamesKeysService.findKeysByGame(idGame);
    }    
    
    @GetMapping("/showActivityGameKeys")
    public List<GameKeys> showActivityGameKeys (GameKeys game){
        return gamesKeysService.showActivityGameKeys(game);
    }
}
