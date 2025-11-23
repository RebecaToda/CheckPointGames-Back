package com.checkpointgames.app.repository;

import com.checkpointgames.app.entity.Itens;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItensRepository extends JpaRepository<Itens, Integer>, ItensRepositoryCustom {

    List<Itens> findByIdOrder_Id(Integer orderId);

    List<Itens> findByIdGame_Id(Integer gameId);
}
