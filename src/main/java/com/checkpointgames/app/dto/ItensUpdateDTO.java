/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.checkpointgames.app.dto;

import java.math.BigDecimal;

/**
 *
 * @author User
 */
public class ItensUpdateDTO {
    private Integer id;        
    private Integer idOrder;     
    private BigDecimal discount;
    private BigDecimal addition;
    private BigDecimal value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
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
