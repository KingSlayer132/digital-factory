package org.challenge.digitalfactory.dto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {
    @Id
    private String id;
    private String nombre;
    private String apellido;
    @Enumerated(EnumType.STRING)
    private EstadoAlumno estado;
    private int edad;
}
