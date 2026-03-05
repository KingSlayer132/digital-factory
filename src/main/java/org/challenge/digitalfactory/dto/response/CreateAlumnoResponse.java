package org.challenge.digitalfactory.dto.response;

public record CreateAlumnoResponse(
        String id,
        String nombre,
        String apellido,
        String estado,
        int edad
) {
}
