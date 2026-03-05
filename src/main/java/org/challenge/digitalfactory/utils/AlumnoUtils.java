package org.challenge.digitalfactory.utils;

import org.challenge.digitalfactory.dto.entity.EstadoAlumno;

public class AlumnoUtils {
    public static EstadoAlumno parseEstado(String estado) {
        try {
            return EstadoAlumno.valueOf(estado.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + estado);
        }
    }
}
