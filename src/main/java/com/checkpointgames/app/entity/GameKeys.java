package com.checkpointgames.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "gamekeys")
public class GameKeys {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @ManyToOne(optional = true)
    @JoinColumn(name = "ID_GAME", referencedColumnName = "ID")
    private Games idGame;
    
    @NotBlank
    @Column(name = "KEY", columnDefinition = "TEXT")
    private String key;
    
    @Column(name = "STATUS", columnDefinition = "integer default 0")
    private Integer status;    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Games getIdGame() {
        return idGame;
    }

    public void setIdGame(Games idGame) {
        this.idGame = idGame;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    

    
}
