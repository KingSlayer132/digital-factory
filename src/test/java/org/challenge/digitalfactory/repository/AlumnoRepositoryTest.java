package org.challenge.digitalfactory.repository;

import org.challenge.digitalfactory.dto.entity.Alumno;
import org.challenge.digitalfactory.dto.entity.EstadoAlumno;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AlumnoRepositoryTest {

    @Autowired
    private AlumnoRepository repository;

    @Test
    void create_and_verify(){
        Alumno alumno = new Alumno("12345678", "Juan", "Perez", EstadoAlumno.ACTIVO, 25);
        repository.save(alumno);

        Alumno found = repository.findById("12345678").orElseThrow();
        assertEquals("Juan", found.getNombre());
        assertEquals("Perez", found.getApellido());
        assertEquals(EstadoAlumno.ACTIVO, found.getEstado());
        assertEquals(25, found.getEdad());
    }


    @Test
    void findByEstado_retorna_alumnos_activos(){
        repository.save(new Alumno("12345678", "Juan", "Perez", EstadoAlumno.ACTIVO, 25));
        repository.save(new Alumno("87654321", "Maria", "Gomez", EstadoAlumno.INACTIVO, 30));
        repository.save(new Alumno("11223344", "Carlos", "Lopez", EstadoAlumno.ACTIVO, 22));

        List<Alumno> activos = repository.findByEstado(EstadoAlumno.ACTIVO);
        assertEquals(2,activos.size());
        assertTrue(activos.stream().allMatch(a -> a.getEstado() == EstadoAlumno.ACTIVO));
    }
}
