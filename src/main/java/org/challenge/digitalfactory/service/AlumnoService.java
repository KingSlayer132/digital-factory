package org.challenge.digitalfactory.service;

import lombok.RequiredArgsConstructor;
import org.challenge.digitalfactory.dto.entity.Alumno;
import org.challenge.digitalfactory.dto.entity.EstadoAlumno;
import org.challenge.digitalfactory.dto.request.CreateAlumnoRequest;
import org.challenge.digitalfactory.dto.response.CreateAlumnoResponse;
import org.challenge.digitalfactory.dto.response.FetchAlumnoReponse;
import org.challenge.digitalfactory.repository.AlumnoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.challenge.digitalfactory.mapper.AlumnoMapper.toEntity;
import static org.challenge.digitalfactory.mapper.AlumnoMapper.toResponse;

@Service
@RequiredArgsConstructor
public class AlumnoService {
    private final AlumnoRepository repository;

    public CreateAlumnoResponse create(CreateAlumnoRequest request) {
        boolean existeAlumno = repository.existsById(request.id());
        if (existeAlumno) {
            throw new IllegalArgumentException("El alumno con id " + request.id() + " ya existe.");
        }
        Alumno alumno = toEntity(request);
        Alumno saved = repository.save(alumno);
        return toResponse(saved);
    }

    public FetchAlumnoReponse fetch() {
        return new FetchAlumnoReponse(repository.findByEstado(EstadoAlumno.ACTIVO));
    }
}
