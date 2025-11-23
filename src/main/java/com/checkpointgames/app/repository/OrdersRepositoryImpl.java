package com.checkpointgames.app.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class OrdersRepositoryImpl implements OrdersRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateOrder(
        Integer costumerId,
        BigDecimal saleValue,
        BigDecimal itensValue,
        BigDecimal discount,
        BigDecimal addition,
        LocalDate date,
        Integer status,
        Integer id
    ) {
        entityManager.createQuery(
            "UPDATE Order o SET " +
                "o.costumer.id = :costumerId, " +
                "o.saleValue = :saleValue, " +
                "o.itensValue = :itensValue, " +
                "o.discount = :discount, " +
                "o.addition = :addition, " +
                "o.date = :date, " +
                "o.status = :status " +
            "WHERE o.id = :id"
        )
        .setParameter("costumerId", costumerId)
        .setParameter("saleValue", saleValue)
        .setParameter("itensValue", itensValue)
        .setParameter("discount", discount)
        .setParameter("addition", addition)
        .setParameter("date", date)
        .setParameter("status", status)
        .setParameter("id", id)
        .executeUpdate();
    }
}
