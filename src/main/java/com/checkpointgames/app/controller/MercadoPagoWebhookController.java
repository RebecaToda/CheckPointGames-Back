package com.checkpointgames.app.controller;

import com.checkpointgames.app.repository.OrdersRepository;
import com.checkpointgames.app.service.PaymentValidationService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/mercadopago")
public class MercadoPagoWebhookController {

    private final PaymentValidationService paymentValidationService;
    private final OrdersRepository ordersRepository;

    public MercadoPagoWebhookController(PaymentValidationService paymentValidationService,
                                        OrdersRepository ordersRepository) {
        this.paymentValidationService = paymentValidationService;
        this.ordersRepository = ordersRepository;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> body) {

        System.out.println("Webhook recebido: " + body);

        try {
            if (!body.containsKey("type") || !body.containsKey("data")) {
                return ResponseEntity.badRequest().body("Webhook invÃ¡lido");
            }

            String type = body.get("type").toString();

            // SÃ³ processa notificaÃ§Ãµes de pagamento
            if (!"payment".equals(type)) {
                return ResponseEntity.ok("Ignorado");
            }

            Map<String, Object> data = (Map<String, Object>) body.get("data");
            Long paymentId = Long.valueOf(data.get("id").toString());

            paymentValidationService.processPayment(paymentId);

            return ResponseEntity.ok("OK");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno");
        }
    }
}