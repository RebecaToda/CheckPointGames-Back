package com.checkpointgames.app.controller;

import com.checkpointgames.app.dto.OrderRequestDTO;
import com.checkpointgames.app.entity.Order;
import com.checkpointgames.app.entity.Users;
import com.checkpointgames.app.repository.UsersRepository;
import com.checkpointgames.app.service.OrderService;
import com.checkpointgames.app.service.PaymentService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OrderRequestDTO orderRequest) {
        try {
            Order saved = orderService.createOrder(orderRequest);

            return ResponseEntity.ok(Map.of(
                    "message", "Pedido criado com sucesso",
                    "orderId", saved.getId(),
                    "paymentLink", saved.getPaymentLink(),
                    "status", 201,
                    "timestamp", LocalDateTime.now().toString()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "message", e.getMessage() != null ? e.getMessage() : "Erro interno no servidor",
                    "status", 500
            ));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getMyOrders() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            Users user = usersRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            List<Order> orders = orderService.showOrderByCostumer(user.getId());

            return ResponseEntity.ok(orders);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Erro ao buscar pedidos: " + e.getMessage()));
        }
    }


    @GetMapping("/verificar-pagamento")
    public String verificarPagamento(@RequestParam String preferenceId) throws MPException, MPApiException {
        paymentService.verificarPagamento(preferenceId);
        return "Consulta de pagamento realizada. Confira o console para detalhes.";
    }

    @PostMapping("/updateOrder")
    public Order updateOrder(@Valid @RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @GetMapping("/showOrders")
    public List<Order> showOrders(Order order) {
        return orderService.showGames(order);
    }

    @GetMapping("/showOpenOrders")
    public List<Order> showOpenOrders(Order order) {
        return orderService.showOpenOrders(order);
    }

    @GetMapping("/showClosedOrders")
    public List<Order> showClosedOrders(Order order) {
        return orderService.showClosedOrders(order);
    }

    @GetMapping("/showCanceledOrders")
    public List<Order> showCanceledOrders(Order order) {
        return orderService.showCanceledOrders(order);
    }

    @GetMapping("/showOrdersByCostumer/{id}")
    public List<Order> showOrderByCostumer(@PathVariable Integer id) {
        return orderService.showOrderByCostumer(id);
    }

    @GetMapping("/showOrdersById/{id}")
    public ResponseEntity<Order> showOrdersById(@PathVariable Integer id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}