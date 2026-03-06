package org.challenge.digitalfactory.exception;

public class InvalidEstadoException extends RuntimeException {
    public InvalidEstadoException(String estado) {
        super("Estado inválido. Solo puede ACTIVO o INACTIVO. Valor recibido: " + estado);
    }
}
