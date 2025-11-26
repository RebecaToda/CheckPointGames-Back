package com.checkpointgames.app.controller;

import com.checkpointgames.app.entity.Users;
import com.checkpointgames.app.dto.UpdatePasswordDTO;
import com.checkpointgames.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public Users saveUser(@Valid @RequestBody Users user){
        return userService.saveUser(user);
    }

    @PostMapping("/updatePassword")
    public Users updatePassword(@Valid @RequestBody UpdatePasswordDTO updatePassword){
        return userService.updatePassword(updatePassword);
    }

    @PostMapping("/updateUser")
    public Users updateUser(@Valid @RequestBody Users user){
        return userService.updateUser(user);
    }
    
    @GetMapping("/showUsers")
    public ResponseEntity<List<Users>> showUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestBody Map<String, Integer> body) {
        Integer status = body.get("status");
        userService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }
}