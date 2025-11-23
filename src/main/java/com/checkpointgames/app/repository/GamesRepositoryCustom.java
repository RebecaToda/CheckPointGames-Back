package com.checkpointgames.app.repository;

import java.math.BigDecimal;

public interface GamesRepositoryCustom {
    void updateGames(String name, String description, String category, Integer inventory, BigDecimal value, Integer discount, Integer status, String linkImage, Integer id);
}