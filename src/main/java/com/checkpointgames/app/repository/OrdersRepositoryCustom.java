package com.checkpointgames.app.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface OrdersRepositoryCustom {

    void updateOrder(Integer costumerId, BigDecimal saleValue, BigDecimal itensValue, BigDecimal discount, BigDecimal addition, LocalDate date, Integer status, Integer id);
}
