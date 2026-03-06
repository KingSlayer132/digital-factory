package org.challenge.digitalfactory.service;

import org.challenge.digitalfactory.dto.entity.Alumno;
import org.challenge.digitalfactory.dto.entity.EstadoAlumno;
import org.challenge.digitalfactory.dto.request.CreateAlumnoRequest;
import org.challenge.digitalfactory.exception.AlumnoAlreadyExistsException;
import org.challenge.digitalfactory.exception.InvalidEstadoException;
import org.challenge.digitalfactory.repository.AlumnoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlumnoServiceTest {
    @Mock
    private AlumnoRepository repository;
    @InjectMocks
    private AlumnoService service;

    @Test
    void create_guardado_exitoso() {
        CreateAlumnoRequest request = new CreateAlumnoRequest(
                "12345678",
                "Juan",
                "Perez",
                "activo",
                25
        );

        Alumno savedAlumno = new Alumno(
                "12345678",
                "Juan",
                "Perez",
                EstadoAlumno.ACTIVO,
                25
        );

        when(repository.existsById("12345678")).thenReturn(false);
        when(repository.save(any(Alumno.class))).thenReturn(savedAlumno);

        StepVerifier.create(service.create(request))
                .verifyComplete();

        ArgumentCaptor<Alumno> alumnoCaptor = ArgumentCaptor.forClass(Alumno.class);
        verify(repository).save(alumnoCaptor.capture());

        Alumno alumnoGuardado = alumnoCaptor.getValue();
        assertEquals("12345678", alumnoGuardado.getId());
        assertEquals("Juan", alumnoGuardado.getNombre());
        assertEquals("Perez", alumnoGuardado.getApellido());
        assertEquals(EstadoAlumno.ACTIVO, alumnoGuardado.getEstado());
        assertEquals(25, alumnoGuardado.getEdad());
    }

    @Test
    void create_falla_registro_existe() {
        CreateAlumnoRequest request = new CreateAlumnoRequest(
                "12345678",
                "Juan",
                "Perez",
                "activo",
                25
        );

        when(repository.existsById("12345678")).thenReturn(true);

        StepVerifier.create(service.create(request))
                .expectError(AlumnoAlreadyExistsException.class)
                .verify();

        verify(repository, never()).save(any(Alumno.class));

    }

    @Test
    void create_falla_estado_invalido() {

        CreateAlumnoRequest request = new CreateAlumnoRequest(
                "12345678",
                "Juan",
                "Perez",
                "pendiente",
                25
        );

        when(repository.existsById("12345678")).thenReturn(false);

        StepVerifier.create(service.create(request))
                .expectError(InvalidEstadoException.class)
                .verify();

        verify(repository, never()).save(any(Alumno.class));
    }

    @Test
    void fetch_exitoso() {

        List<Alumno> alumnos = List.of(
                new Alumno("12345678", "Juan", "Perez", EstadoAlumno.ACTIVO, 25),
                new Alumno("87654321", "Ana", "Lopez", EstadoAlumno.ACTIVO, 30)
        );

        when(repository.findByEstado(EstadoAlumno.ACTIVO)).thenReturn(alumnos);

        StepVerifier.create(service.fetch())
                .assertNext(response -> {
                    assertNotNull(response.alumnos());
                    assertEquals(2, response.alumnos().size());
                    assertEquals("12345678", response.alumnos().get(0).getId());
                    assertEquals("87654321", response.alumnos().get(1).getId());
                })
                .verifyComplete();

        verify(repository).findByEstado(EstadoAlumno.ACTIVO);

    }
}
