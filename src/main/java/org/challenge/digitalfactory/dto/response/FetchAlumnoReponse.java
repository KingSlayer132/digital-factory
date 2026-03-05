package org.challenge.digitalfactory.dto.response;

import org.challenge.digitalfactory.dto.entity.Alumno;

import java.util.List;

public record FetchAlumnoReponse(List<Alumno> alumnos) {
}
