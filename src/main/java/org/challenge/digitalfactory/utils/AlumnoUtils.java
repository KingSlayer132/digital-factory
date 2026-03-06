package org.challenge.digitalfactory.utils;

import org.challenge.digitalfactory.dto.entity.EstadoAlumno;
import org.challenge.digitalfactory.exception.InvalidEstadoException;

public class AlumnoUtils {
    public static EstadoAlumno parseEstado(String estado) {
        try {
            return EstadoAlumno.valueOf(estado.trim().toUpperCase());
        } catch (IllegalArgumentException  e) {
            throw new InvalidEstadoException(estado);
        }
    }
}
