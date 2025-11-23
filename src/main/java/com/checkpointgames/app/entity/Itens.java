package com.checkpointgames.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "ITENS")
public class Itens {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @ManyToOne(optional = true)
    @JoinColumn(name = "ID_GAME", referencedColumnName = "ID")
    private Games idGame;

    @NotNull
    @ManyToOne(optional = true)
    @JoinColumn(name = "ID_ORDER", referencedColumnName = "ID")
    private Order idOrder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_KEY", referencedColumnName = "ID")
    private GameKeys idKey;

    @Column(name = "DISCOUNT", precision = 15, scale = 3)
    private BigDecimal discount;
    
    @Column(name = "ADDITION", precision = 15, scale = 3)
    private BigDecimal addition;
    
    @Column(name = "VALUE", precision = 15, scale = 3)
    private BigDecimal value;

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

    public Order getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Order idOrder) {
        this.idOrder = idOrder;
    }

    public GameKeys getIdKey() {
        return idKey;
    }

    public void setIdKey(GameKeys idKey) {
        this.idKey = idKey;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getAddition() {
        return addition;
    }

    public void setAddition(BigDecimal addition) {
        this.addition = addition;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
            
    
}
