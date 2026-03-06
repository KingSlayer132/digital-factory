package org.challenge.digitalfactory.controller;

import org.challenge.digitalfactory.dto.entity.Alumno;
import org.challenge.digitalfactory.dto.entity.EstadoAlumno;
import org.challenge.digitalfactory.dto.response.FetchAlumnoReponse;
import org.challenge.digitalfactory.exception.AlumnoAlreadyExistsException;
import org.challenge.digitalfactory.exception.ApiExceptionHandler;
import org.challenge.digitalfactory.exception.InvalidEstadoException;
import org.challenge.digitalfactory.service.AlumnoService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = AlumnoController.class)
@Import(ApiExceptionHandler.class)
public class AlumnoControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private AlumnoService service;

    public static final String URI_ALUMNOS = "/alumnos";

    @Test
    void create_alumno_exitoso_201() {
        when(service.create(ArgumentMatchers.any())).thenReturn(Mono.empty());
        webTestClient.post()
                .uri(URI_ALUMNOS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "id": "12345678",
                            "nombre": "Juan",
                            "apellido": "Perez",
                            "estado": "activo",
                            "edad": 25
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    @Test
    void create_invalido_error_request_body() {

        webTestClient.post()
                .uri(URI_ALUMNOS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                           "id":"1234567",
                           "nombre":"Juan",
                           "apellido":"Perez",
                           "estado":"activo",
                           "edad":25
                        }
                        """).exchange()
                .expectStatus().isBadRequest().
                expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.fieldErrors.id").exists();
    }

    @Test
    void create_error_duplicado() {
        when(service.create(ArgumentMatchers.any()))
                .thenReturn(Mono.error(new AlumnoAlreadyExistsException("12345678")));

        webTestClient.post()
                .uri(URI_ALUMNOS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "id": "12345678",
                            "nombre": "Juan",
                            "apellido": "Perez",
                            "estado": "activo",
                            "edad": 25
                        }
                        """)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.status").isEqualTo(HttpStatus.CONFLICT.value())
                .jsonPath("$.message").value(msg -> ((String) msg).contains("12345678"));

    }


    @Test
    void create_error_estado_invalido() {
        when(service.create(ArgumentMatchers.any()))
                .thenReturn(Mono.error(new InvalidEstadoException("pendiente")));

        webTestClient.post()
                .uri(URI_ALUMNOS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "id":"12345678",
                          "nombre":"Juan",
                          "apellido":"Perez",
                          "estado":"pendiente",
                          "edad":25
                        }
                        """)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.message").exists();
    }

    @Test
    void fetch_exitoso() {
        var alumnos = List.of(
                new Alumno("12345678", "Juan", "Perez", EstadoAlumno.ACTIVO, 25),
                new Alumno("87654321", "Ana", "Lopez", EstadoAlumno.ACTIVO, 30)
        );

        when(service.fetch()).thenReturn(Mono.just(new FetchAlumnoReponse(alumnos)));

        webTestClient.get()
                .uri(URI_ALUMNOS)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.alumnos.length()").isEqualTo(2)
                .jsonPath("$.alumnos[0].id").isEqualTo("12345678")
                .jsonPath("$.alumnos[1].id").isEqualTo("87654321");
    }
}
