package com.checkpointgames.app.controller;

import com.checkpointgames.app.dto.ItensUpdateDTO;
import com.checkpointgames.app.entity.Itens;
import com.checkpointgames.app.service.ItensService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/itens")
public class ItensController {

    @Autowired
    private ItensService itensService;

    @PostMapping("/createItem")
    public Itens saveItens(@Valid @RequestBody Itens itens) {
        return itensService.saveItens(itens);
    }

    @PostMapping("/updateItem")
    public Itens updateItens(@Valid @RequestBody ItensUpdateDTO itens) {
        return itensService.updateItem(itens);
    }

    @GetMapping("/showItens")
    public List<Itens> showItens() {
        return itensService.findAll();
    }

    @GetMapping("/showItensByOrder/{orderId}")
    public List<Itens> findByOrder(@PathVariable Integer orderId) {
        return itensService.findByOrder(orderId);
    }

    @GetMapping("/showItensByGame/{gameId}")
    public List<Itens> findByGame(@PathVariable Integer gameId) {
        return itensService.findByGame(gameId);
    }

    @GetMapping("/showItensById/{id}")
    public ResponseEntity<Itens> findById(@PathVariable Integer id) {
        return itensService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
