
package com.checkpointgames.app.dto;

public class ItensCreateDTO {

    private Integer idGame;      // obrigatório
    private Integer idOrder;     // obrigatório
    private Double discount;
    private Double addition;
    private Double value;

    public Integer getIdGame() {
        return idGame;
    }

    public void setIdGame(Integer idGame) {
        this.idGame = idGame;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAddition() {
        return addition;
    }

    public void setAddition(Double addition) {
        this.addition = addition;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    
}
