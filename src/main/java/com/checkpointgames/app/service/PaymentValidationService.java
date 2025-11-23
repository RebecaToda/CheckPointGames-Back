package com.checkpointgames.app.service;

import com.checkpointgames.app.entity.Order;
import com.checkpointgames.app.repository.OrdersRepository;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentValidationService {

    private final OrdersRepository ordersRepository;

    public PaymentValidationService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void processPayment(Long paymentId) throws Exception {
        PaymentClient client = new PaymentClient();
        Payment payment = client.get(paymentId);

        String status = payment.getStatus(); // approved, rejected, etc.

        System.out.println("STATUS DO PAGAMENTO: " + status);

        // ignore se não for aprovado
        if (!"approved".equalsIgnoreCase(status)) {
            return;
        }

        // O pedido está no campo external_reference
        Long orderId = Long.valueOf(payment.getExternalReference());

        Order order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        order.setStatus(1); // <-- ALTERA O STATUS AQUI
        ordersRepository.save(order);

        System.out.println("Pedido " + orderId + " atualizado como PAGO.");
    }
    
}
