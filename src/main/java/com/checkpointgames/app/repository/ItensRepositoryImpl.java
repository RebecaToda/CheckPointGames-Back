package com.checkpointgames.app.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ItensRepositoryImpl implements ItensRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateItens(Integer idGame, Integer idOrder, Integer idKey,
                            BigDecimal discount, BigDecimal addition, BigDecimal value,
                            Integer id) {

        entityManager.createQuery("""
            UPDATE Itens i 
            SET i.idGame.id = :idGame,
                i.idOrder.id = :idOrder,
                i.idKey.id = :idKey,
                i.discount = :discount,
                i.addition = :addition,
                i.value = :value
            WHERE i.id = :id
        """)
                .setParameter("idGame", idGame)
                .setParameter("idOrder", idOrder)
                .setParameter("idKey", idKey)
                .setParameter("discount", discount)
                .setParameter("addition", addition)
                .setParameter("value", value)
                .setParameter("id", id)
                .executeUpdate();
    }
}