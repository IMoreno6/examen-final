package com.proyecto.evaluacion.repository;

import com.proyecto.evaluacion.model.Pregunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {

    List<Pregunta> findByEnunciadoContainingIgnoreCase(String enunciado);

    List<Pregunta> findByPuntajeBetween(Double puntajeMin, Double puntajeMax);

    Page<Pregunta> findByTematicaId(Long tematicaId, Pageable pageable);

    Page<Pregunta> findByTipo(String tipo, Pageable pageable);

    Page<Pregunta> findByTematicaIdAndTipo(Long tematicaId, String tipo, Pageable pageable);
}