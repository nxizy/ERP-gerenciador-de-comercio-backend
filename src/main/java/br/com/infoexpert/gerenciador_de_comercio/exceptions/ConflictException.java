package br.com.infoexpert.gerenciador_de_comercio.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
