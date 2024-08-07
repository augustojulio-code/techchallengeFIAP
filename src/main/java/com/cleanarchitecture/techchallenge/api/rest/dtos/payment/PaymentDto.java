package com.cleanarchitecture.techchallenge.api.rest.dtos.payment;

import com.cleanarchitecture.techchallenge.api.rest.dtos.client.ClientDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder(toBuilder = true, setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentDto {

    private String type;
    private ClientDto client;
    private boolean paid;
    private String paidAt;
    private Map<String, Object> paymentDetails;
}
