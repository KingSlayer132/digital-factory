package org.challenge.digitalfactory.mapper;

import org.challenge.digitalfactory.dto.entity.Alumno;
import org.challenge.digitalfactory.dto.entity.EstadoAlumno;
import org.challenge.digitalfactory.dto.request.CreateAlumnoRequest;
import org.challenge.digitalfactory.dto.response.CreateAlumnoResponse;

import static org.challenge.digitalfactory.utils.AlumnoUtils.parseEstado;

public class AlumnoMapper {

    public static Alumno toEntity(CreateAlumnoRequest request) {
        return new Alumno(
                request.id(),
                request.nombre().trim(),
                request.apellido().trim(),
                parseEstado(request.estado()),
                request.edad()
        );
    }

    public static CreateAlumnoResponse toResponse(Alumno alumno) {
        return new CreateAlumnoResponse(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getEstado().name(),
                alumno.getEdad()
        );
    }


}
