package br.com.infoexpert.gerenciador_de_comercio.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum PaymentMethod {
    CARD,
    CASH,
    PIX,
    BILL;

    @JsonCreator
    public static PaymentMethod from(String value) {
        return Arrays.stream(PaymentMethod.values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status inválido: " + value));
    }
}
