package com.checkpointgames.app.service;

import com.checkpointgames.app.dto.OrderRequestDTO;
import com.checkpointgames.app.dto.OrderItemRequestDTO;
import com.checkpointgames.app.entity.*;
import com.checkpointgames.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private GamesKeysRepository gameKeysRepository;
    @Autowired
    private ItensRepository itensRepository;
    @Autowired
    private PaymentService paymentService;

    @Transactional
    public Order createOrder(OrderRequestDTO request) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        Order order = new Order();
        order.setCostumer(user);
        order.setDate(LocalDate.now());
        order.setStatus(0); // Pendente


        BigDecimal totalOrder = BigDecimal.ZERO;
        order.setSaleValue(BigDecimal.ZERO);
        order.setItensValue(BigDecimal.ZERO);


        order = ordersRepository.save(order);


        for (OrderItemRequestDTO itemDto : request.getItems()) {
            Games game = gamesRepository.findById(itemDto.getGameId())
                    .orElseThrow(() -> new RuntimeException("Jogo não encontrado: " + itemDto.getGameId()));


            List<GameKeys> availableKeys = gameKeysRepository.findAvailableKeys(game.getId());
            if (availableKeys.size() < itemDto.getQuantity()) {
                throw new RuntimeException("Estoque insuficiente para o jogo: " + game.getName());
            }


            for (int i = 0; i < itemDto.getQuantity(); i++) {
                GameKeys key = availableKeys.get(i);


                key.setStatus(1);
                gameKeysRepository.save(key);


                Itens item = new Itens();
                item.setIdOrder(order);
                item.setIdGame(game);
                item.setIdKey(key);


                BigDecimal price = game.getFinalPrice();
                item.setValue(price);
                item.setDiscount(BigDecimal.valueOf(game.getDiscount()));
                item.setAddition(BigDecimal.ZERO);

                itensRepository.save(item);


                totalOrder = totalOrder.add(price);


                game.setInventory(game.getInventory() - 1);
                gamesRepository.save(game);
            }
        }


        order.setSaleValue(totalOrder);
        order.setItensValue(totalOrder);


        String paymentLink = paymentService.createCheckoutPreference(
                order.getId(),
                "Pedido #" + order.getId(),
                1,
                totalOrder
        );
        order.setPaymentLink(paymentLink);

        return ordersRepository.save(order);
    }

    public List<Order> showGames(Order order) { return ordersRepository.findAll(); }
    public List<Order> showOpenOrders(Order order){ return ordersRepository.findOpenOrders(); }
    public List<Order> showClosedOrders(Order order){ return ordersRepository.findClosedOrders(); }
    public List<Order> showCanceledOrders(Order order){ return ordersRepository.findCanceledOrders(); }
    public List<Order> showOrderByCostumer(Integer id) { return ordersRepository.findByClient(id); }
    public Optional<Order> findById(Integer id) { return ordersRepository.findById(id); }

    public Order updateOrder(Order order) { return ordersRepository.save(order); }
}