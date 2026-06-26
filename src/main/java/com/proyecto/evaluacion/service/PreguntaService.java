package com.proyecto.evaluacion.service;

import com.proyecto.evaluacion.model.Pregunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PreguntaService {

    List<Pregunta> listarTodos();

    Page<Pregunta> listarPaginado(Pageable pageable);

    Optional<Pregunta> buscarPorId(Long id);

    Pregunta guardar(Pregunta pregunta);

    Pregunta actualizar(Long id, Pregunta pregunta);

    void eliminarPorId(Long id);

    List<Pregunta> buscarPorEnunciado(String enunciado);

    List<Pregunta> buscarPorRangoDePuntaje(Double puntajeMin, Double puntajeMax);

    Page<Pregunta> buscarPorFiltros(Long tematicaId, String tipo, Pageable pageable);
}