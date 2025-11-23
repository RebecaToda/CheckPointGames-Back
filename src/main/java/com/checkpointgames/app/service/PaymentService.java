package com.checkpointgames.app.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    private final PaymentClient paymentClient;

    // Construtor que pega o token do arquivo application.properties automaticamente
    public PaymentService(@Value("${mercadopago.access-token}") String accessToken) {
        // Configura o token globalmente para o SDK
        MercadoPagoConfig.setAccessToken(accessToken);
        this.paymentClient = new PaymentClient();
        System.out.println("Mercado Pago configurado com token: " + accessToken.substring(0, 10) + "...");
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

            // Mudei para google.com temporariamente para garantir que o MP não rejeite "localhost"
            // Depois que funcionar, você pode tentar voltar para localhost
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://www.google.com")
                    .pending("https://www.google.com")
                    .failure("https://www.google.com")
                    .build();

            PreferenceRequest request = PreferenceRequest.builder()
                    .externalReference(String.valueOf(orderId))
                    .items(List.of(item))
                    .backUrls(backUrls)
                    // .autoReturn("approved")  <-- REMOVIDO: Isso estava causando o erro 400
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(request);

            System.out.println("Link gerado: " + preference.getInitPoint());
            return preference.getInitPoint();

        } catch (MPApiException e) {
            System.err.println("ERRO MERCADO PAGO API: " + e.getApiResponse().getContent());
            throw e;
        } catch (Exception e) {
            System.err.println("ERRO GENÉRICO: " + e.getMessage());
            throw e;
        }
    }

    public void verificarPagamento(String preferenceId) throws MPException, MPApiException {
        Map<String, Object> filtros = new HashMap<>();
        filtros.put("preference_id", preferenceId);

        MPSearchRequest request = MPSearchRequest.builder()
                .filters(filtros)
                .limit(50)
                .offset(0)
                .build();

        MPResultsResourcesPage<Payment> page = paymentClient.search(request);
        List<Payment> pagamentos = page.getResults();

        if (!pagamentos.isEmpty()) {
            for (Payment p : pagamentos) {
                System.out.println("Pagamento ID: " + p.getId() + " | Status: " + p.getStatus());
            }
        }
    }
}