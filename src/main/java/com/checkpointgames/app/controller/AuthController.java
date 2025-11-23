package com.checkpointgames.app.controller;

import com.checkpointgames.app.entity.Users;
import com.checkpointgames.app.repository.UsersRepository;
import com.checkpointgames.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    private UsersRepository usersRepository;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        // Gera o token
        String token = authService.login(email, password);

        // Busca o usuário completo para devolver para o Front
        Optional<Users> userOptional = usersRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", user,
                    "message", "Login efetuado com sucesso",
                    "status", 200,
                    "timestamp", LocalDateTime.now().toString()
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Erro ao recuperar dados do usuário"));
        }
    }
}