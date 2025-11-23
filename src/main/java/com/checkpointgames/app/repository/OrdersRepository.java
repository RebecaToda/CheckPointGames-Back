package com.checkpointgames.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.checkpointgames.app.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersRepository extends JpaRepository<Order, Integer>, OrdersRepositoryCustom{

    Optional<Order> findById(Long id);

    @Query(value = "SELECT * FROM orders where status = '0'", nativeQuery = true)
    List<Order> findOpenOrders();
  
    @Query(value = "SELECT * FROM orders where status = '1'", nativeQuery = true)
    List<Order> findClosedOrders();
    
    @Query(value = "SELECT * FROM orders where status = '2'", nativeQuery = true)
    List<Order> findCanceledOrders();
    
    @Query(value = "SELECT * FROM orders where id_costumer = :clientId", nativeQuery = true)
    List<Order> findByClient(@Param("clientId") Integer idCostumer);    
        
        
}
