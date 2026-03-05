package org.challenge.digitalfactory.repository;

import org.challenge.digitalfactory.dto.entity.Alumno;
import org.challenge.digitalfactory.dto.entity.EstadoAlumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    List<Alumno> findByEstado(EstadoAlumno estado);
}
