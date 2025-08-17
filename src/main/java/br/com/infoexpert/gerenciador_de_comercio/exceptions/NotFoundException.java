package br.com.infoexpert.gerenciador_de_comercio.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
