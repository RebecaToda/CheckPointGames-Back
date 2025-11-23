package com.checkpointgames.app.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class GamesRepositoryImpl implements GamesRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateGames(String name, String description, String category, Integer inventory, BigDecimal value, Integer discount, Integer status, String linkImage, Integer id){
        entityManager.createQuery("UPDATE Games g set g.name = :name, g.description = :description, g.category = :category, g.inventory= :inventory, g.value = :value, g.discount = :discount, g.status = :status, g.linkImage= :linkImage where g.id = :id")
                .setParameter("name", name)
                .setParameter("description", description)
                .setParameter("category", category)
                .setParameter("inventory", inventory)
                .setParameter("value", value)
                .setParameter("discount", discount)
                .setParameter("status", status)
                .setParameter("linkImage", linkImage)
                .setParameter("id", id)
                .executeUpdate();
    }
}