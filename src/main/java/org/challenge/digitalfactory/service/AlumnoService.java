package org.challenge.digitalfactory.service;

import lombok.RequiredArgsConstructor;
import org.challenge.digitalfactory.dto.entity.Alumno;
import org.challenge.digitalfactory.dto.entity.EstadoAlumno;
import org.challenge.digitalfactory.dto.request.CreateAlumnoRequest;
import org.challenge.digitalfactory.dto.response.CreateAlumnoResponse;
import org.challenge.digitalfactory.dto.response.FetchAlumnoReponse;
import org.challenge.digitalfactory.exception.AlumnoAlreadyExistsException;
import org.challenge.digitalfactory.repository.AlumnoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.challenge.digitalfactory.mapper.AlumnoMapper.toEntity;
import static org.challenge.digitalfactory.mapper.AlumnoMapper.toResponse;

@Service
@RequiredArgsConstructor
public class AlumnoService {
    private final AlumnoRepository repository;

    public Mono<Void> create(CreateAlumnoRequest request) {
        return Mono.fromRunnable(() -> {
            boolean existeAlumno = repository.existsById(request.id());
            if (existeAlumno) {
                throw new AlumnoAlreadyExistsException(request.id());
            }
            Alumno alumno = toEntity(request);
            repository.save(alumno);

        }).subscribeOn(Schedulers.boundedElastic()).then();

    }

    public Mono<FetchAlumnoReponse> fetch() {
        return Mono.fromCallable(() -> {
            return new FetchAlumnoReponse(repository.findByEstado(EstadoAlumno.ACTIVO));
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
