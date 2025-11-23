package com.checkpointgames.app.dto;

public class OrderItemRequestDTO {
    private Integer gameId;
    private Integer quantity;

    public Integer getGameId() { return gameId; }
    public void setGameId(Integer gameId) { this.gameId = gameId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}