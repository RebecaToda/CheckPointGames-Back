package com.checkpointgames.app.controller;

import com.checkpointgames.app.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/webhook")
public class MercadoPagoWebhookController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/mercadopago")
    public ResponseEntity<?> receiveNotification(@RequestBody Map<String, Object> payload, @RequestParam(required = false) String topic, @RequestParam(required = false) Long id) {


        try {
            Long paymentId = null;


            if (id != null) {
                paymentId = id;
            }

            else if (payload.containsKey("data") && payload.get("data") instanceof Map) {
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                if (data.containsKey("id")) {
                    paymentId = Long.valueOf(data.get("id").toString());
                }
            }

            else if ("payment".equals(payload.get("type")) && payload.containsKey("data.id")) {
                paymentId = Long.valueOf(payload.get("data.id").toString());
            }

            if (paymentId != null) {
                System.out.println("Webhook recebido para Pagamento ID: " + paymentId);
                
                paymentService.processPaymentNotification(paymentId);
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}