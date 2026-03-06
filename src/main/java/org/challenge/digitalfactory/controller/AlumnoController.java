package org.challenge.digitalfactory.controller;

import jakarta.validation.Valid;
import org.challenge.digitalfactory.dto.request.CreateAlumnoRequest;
import org.challenge.digitalfactory.dto.response.FetchAlumnoReponse;
import org.challenge.digitalfactory.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    private final AlumnoService service;


    public AlumnoController(AlumnoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createAlumno(@Valid @RequestBody CreateAlumnoRequest request) {
        return service.create(request).then();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<FetchAlumnoReponse> fetchAlumno() {
        return service.fetch();
    }
}
