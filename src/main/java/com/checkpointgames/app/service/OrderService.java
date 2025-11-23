package com.checkpointgames.app.service;

import com.checkpointgames.app.entity.Order;
import com.checkpointgames.app.exception.InvalidIdException;
import com.checkpointgames.app.repository.OrdersRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    @Autowired
    private OrdersRepository ordersRepository;
    
    @Autowired
    private PaymentService paymentService;
    
    public Order saveOrder(Order order) throws Exception{
        Order saved = ordersRepository.save(order);
        
        String paymentLink = paymentService.createCheckoutPreference(
                saved.getId(),
                "Pedido: " + saved.getId(),
                1,
                saved.getSaleValue()
        );
        
        saved.setPaymentLink(paymentLink);
        ordersRepository.save(saved);
        
        return saved;
    }
    
    public Order updateOrder(Order order) {
        ordersRepository.findById(order.getId())
            .orElseThrow(() -> new InvalidIdException("Pedido não encontrado"));
        
        ordersRepository.updateOrder(order.getCostumer().getId(), order.getSaleValue(), order.getItensValue(), order.getDiscount(), order.getAddition(), order.getDate(), order.getStatus(), order.getId());

        
        return ordersRepository.findById(order.getId())
            .orElseThrow(() -> new RuntimeException("Erro ao atualizar pedido"));
    }    
    
    public List<Order> showGames(Order order) {
        return ordersRepository.findAll();
    }
    
    public List<Order> showOpenOrders (Order order){
        return ordersRepository.findOpenOrders();
    }
    
    public List<Order> showClosedOrders (Order order){
        return ordersRepository.findClosedOrders();
    }

    public List<Order> showCanceledOrders (Order order){
        return ordersRepository.findCanceledOrders();
    }    
    
    public List<Order> showOrderByCostumer (Integer id) {
        return ordersRepository.findByClient(id);
    }
    
    public Optional<Order> findById (Integer id) {
        return ordersRepository.findById(id);
    }
    
}
