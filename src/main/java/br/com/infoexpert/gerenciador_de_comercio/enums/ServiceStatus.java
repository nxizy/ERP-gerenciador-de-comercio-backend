package br.com.infoexpert.gerenciador_de_comercio.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ServiceStatus {
    OPEN,
    IN_PROGRESS,
    CLOSED,
    CANCELLED;

    @JsonCreator
    public static ServiceStatus from(String value) {
        return Arrays.stream(ServiceStatus.values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status inválido: " + value));
    }
}
