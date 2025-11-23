package com.checkpointgames.app.service;

import com.checkpointgames.app.entity.Users;
import com.checkpointgames.app.exception.InvalidCredentialsException;
import com.checkpointgames.app.repository.UsersRepository;
import com.checkpointgames.app.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsersRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UsersRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String password) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Email ou senha inválidos");
        }

        return jwtUtil.generateToken(email);
    }
}
