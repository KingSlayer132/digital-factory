package org.challenge.digitalfactory.dto.request;

import jakarta.validation.constraints.*;

public record CreateAlumnoRequest(
        @NotBlank(message = "El id es obligatorio")
        @Pattern(regexp = "\\d{8}", message = "El id debe tener exactamente 8 dígitos")
        String id,
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @NotBlank(message = "El apellido es obligatorio")
        String apellido,
        @NotBlank(message = "El estado es obligatorio")
        String estado,
        @NotNull(message = "La edad es obligatoria")
        @Min(value = 1, message = "La edad debe ser mayor a 0")
        @Max(value = 100, message = "La edad no puede ser mayor a 100")
        Integer edad
) {}
