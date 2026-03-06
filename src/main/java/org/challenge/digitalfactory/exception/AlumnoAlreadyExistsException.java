package org.challenge.digitalfactory.exception;


public class AlumnoAlreadyExistsException extends RuntimeException {
    public AlumnoAlreadyExistsException(String id) {
        super("El alumno con id " + id + " ya existe.");
    }
}

