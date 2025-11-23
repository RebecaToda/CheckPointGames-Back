package com.checkpointgames.app.repository;

import java.math.BigDecimal;

public interface ItensRepositoryCustom {

    void updateItens(Integer idGame, Integer idOrder, Integer idKey,
                     BigDecimal discount, BigDecimal addition, BigDecimal value,
                     Integer id);
}
