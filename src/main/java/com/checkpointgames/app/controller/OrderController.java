package com.checkpointgames.app.controller;

import com.checkpointgames.app.dto.OrderRequestDTO; // Import do DTO novo
import com.checkpointgames.app.entity.Order;
import com.checkpointgames.app.service.OrderService;
import com.checkpointgames.app.service.PaymentService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OrderRequestDTO orderRequest) { // Usa o DTO
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
            e.printStackTrace(); // <--- ISSO VAI MOSTRAR O ERRO VERMELHO NO CONSOLE
            return ResponseEntity.status(500).body(Map.of(
                    "message", e.getMessage() != null ? e.getMessage() : "Erro interno no servidor", // <--- Mudei de "error" para "message" para o Front ler
                    "status", 500
            ));
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