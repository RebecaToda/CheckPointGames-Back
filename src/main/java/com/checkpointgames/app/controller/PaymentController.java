package com.checkpointgames.app.controller;

import com.checkpointgames.app.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody Map<String, Object> body) {
        try {
            Integer orderId = Integer.parseInt(body.get("orderId").toString());
            String title = body.get("title").toString();
            Integer quantity = Integer.parseInt(body.get("quantity").toString());
            BigDecimal value = new BigDecimal(body.get("value").toString());

            String checkoutUrl = paymentService.createCheckoutPreference(orderId, title, quantity, value);

            return ResponseEntity.ok(Map.of(
                "checkoutUrl", checkoutUrl,
                "message", "Link gerado com sucesso"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
