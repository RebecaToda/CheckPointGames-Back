package com.checkpointgames.app.service;

import com.checkpointgames.app.entity.Order;
import com.checkpointgames.app.repository.OrdersRepository;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentClient paymentClient;

    @Autowired
    private OrdersRepository ordersRepository;

    public PaymentService(@Value("${mercadopago.access-token}") String accessToken) {
        MercadoPagoConfig.setAccessToken(accessToken);
        this.paymentClient = new PaymentClient();
        System.out.println("Mercado Pago configurado!");
    }

    public String createCheckoutPreference(Integer orderId, String title, Integer quantity, BigDecimal value) throws Exception {

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("O valor do pedido deve ser maior que zero.");
        }

        try {
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(title)
                    .quantity(quantity)
                    .unitPrice(value)
                    .currencyId("BRL")
                    .build();


            String baseUrl = "https://nonderivable-jennell-overgesticulatively.ngrok-free.dev";

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success(baseUrl + "/payment/callback")
                    .pending(baseUrl + "/payment/callback")
                    .failure(baseUrl + "/payment/callback")
                    .build();

            PreferenceRequest request = PreferenceRequest.builder()
                    .externalReference(String.valueOf(orderId))
                    .items(List.of(item))
                    .backUrls(backUrls)
                    .autoReturn("approved")

                    .notificationUrl(baseUrl + "/api/v1/webhook/mercadopago")
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(request);

            return preference.getInitPoint();

        } catch (MPApiException e) {
            System.err.println("ERRO API MERCADO PAGO: " + e.getApiResponse().getContent());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public void processPaymentNotification(Long paymentId) {
        try {

            Payment payment = paymentClient.get(paymentId);

            System.out.println("Notificação recebida. Pagamento ID: " + paymentId + " Status: " + payment.getStatus());


            if ("approved".equals(payment.getStatus())) {

                String orderIdStr = payment.getExternalReference();

                if (orderIdStr != null) {
                    Integer orderId = Integer.parseInt(orderIdStr);


                    Order order = ordersRepository.findById(orderId).orElse(null);

                    if (order != null && order.getStatus() == 0) {

                        order.setStatus(1);
                        ordersRepository.save(order);
                        System.out.println("Pedido #" + orderId + " atualizado para CONCLUÍDO!");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar notificação de pagamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void verificarPagamento(String preferenceId) throws MPException, MPApiException {
    }
}