package com.checkpointgames.app.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentClient paymentClient;


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


            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://nonderivable-jennell-overgesticulatively.ngrok-free.dev/")
                    .pending("https://nonderivable-jennell-overgesticulatively.ngrok-free.dev/")
                    .failure("https://nonderivable-jennell-overgesticulatively.ngrok-free.dev/")
                    .build();

            PreferenceRequest request = PreferenceRequest.builder()
                .externalReference(String.valueOf(orderId))
                .items(List.of(item))
                .backUrls(backUrls)
                .autoReturn("approved")
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

    public void verificarPagamento(String preferenceId) throws MPException, MPApiException {

    }
}