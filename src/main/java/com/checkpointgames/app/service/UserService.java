package com.checkpointgames.app.service;

import com.checkpointgames.app.dto.UpdatePasswordDTO;
import com.checkpointgames.app.entity.Users;
import com.checkpointgames.app.exception.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.checkpointgames.app.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService{
    
    @Autowired
    private UsersRepository usersRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Users saveUser(Users user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        
        return usersRepository.save(user);
    }
    
    public Users updatePassword(UpdatePasswordDTO updatePassword) {
        usersRepository.findByEmail(updatePassword.getEmail())
            .orElseThrow(() -> new InvalidCredentialsException("Usuário não encontrado"));        
        
        String encrypted = passwordEncoder.encode(updatePassword.getPassword());
        usersRepository.updatePassword(updatePassword.getEmail(), encrypted);
        
        return usersRepository.findByEmail(updatePassword.getEmail())
            .orElseThrow(() -> new RuntimeException("Erro ao atualizar a senha"));
    }    
    
    public Users updateUser(Users user){
        usersRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new InvalidCredentialsException("Usuário não encontrado"));        
        
        String encrypted = passwordEncoder.encode(user.getPassword());
        usersRepository.updateUser(user.getEmail(), user.getName(), user.getAge(), user.getFunction(), user.getStatus(), encrypted, user.getNumber(), user.getId());        
    
        return usersRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new RuntimeException("Erro ao atualizar usuário"));
    }  
            
}

