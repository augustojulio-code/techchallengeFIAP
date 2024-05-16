package com.aquiteturahexa.techchallenge.adapters.persistence;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import jakarta.servlet.http.HttpServletResponse;

public class PaymentAdapter {
    private String accessToken = "";

    private PreferenceClient client = new PreferenceClient();

    public String getFakeCheout() {

        MercadoPagoConfig.setAccessToken(accessToken);
        try {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id("")
                    .title("")
                    .quantity(1)
                    .unitPrice(new BigDecimal("10"))
                    .currencyId("BRL")
                    .build();

            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://test.com/success")
                    .failure("http://test.com/failure")
                    .pending("http://test.com/pending")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .backUrls(backUrls)
                    .items(items)
                    .build();

            Preference preference = client.create(preferenceRequest);

            return preference.getInitPoint();

        } catch (Exception e) {
            return e.getMessage();

        }
    }

    public String generateQRCode() {

        MercadoPagoConfig.setAccessToken(accessToken);

        PaymentClient paymentClient = new PaymentClient();

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(new BigDecimal(10))
                .description("teste")
                .paymentMethodId("pix")
                .payer(
                        PaymentPayerRequest.builder()
                                .email("teste@test.com")
                                .firstName("ze da mina")
                                .lastName("abandonada")
                                .build())
                .build();

        try {
            Payment createPayment = paymentClient.create(paymentCreateRequest);
            return createPayment.getPointOfInteraction().getTransactionData().getQrCode();
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public void convertToQRCode(HttpServletResponse response) throws IOException, WriterException {

        String pixCode = generateQRCode();

        BitMatrix bitMatrix = new MultiFormatWriter().encode(pixCode, BarcodeFormat.QR_CODE, 450, 450);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
        byte[] qrCodeBytes = outputStream.toByteArray();

        response.setContentType("image/png");
        response.setContentLength(qrCodeBytes.length);

        OutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(qrCodeBytes);
        responseOutputStream.flush();
        responseOutputStream.close();

    }

    public void paymentChekout(HttpServletResponse response) throws IOException {

        response.sendRedirect(getFakeCheout());
    }

}
