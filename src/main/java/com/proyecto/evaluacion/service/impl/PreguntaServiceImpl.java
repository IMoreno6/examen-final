package com.proyecto.evaluacion.service.impl;

import com.proyecto.evaluacion.exception.PreguntaNoEncontradaException;
import com.proyecto.evaluacion.model.Pregunta;
import com.proyecto.evaluacion.repository.PreguntaRepository;
import com.proyecto.evaluacion.repository.TematicaRepository;
import com.proyecto.evaluacion.service.PreguntaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreguntaServiceImpl implements PreguntaService {

    private final PreguntaRepository preguntaRepository;

    public PreguntaServiceImpl(PreguntaRepository preguntaRepository) {
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public List<Pregunta> listarTodos() {
        return preguntaRepository.findAll();
    }

    @Override
    public Page<Pregunta> listarPaginado(Pageable pageable) {
        return preguntaRepository.findAll(pageable);
    }

    @Override
    public Optional<Pregunta> buscarPorId(Long id) {
        return preguntaRepository.findById(id);
    }

    @Override
    public Pregunta guardar(Pregunta pregunta) {
        return preguntaRepository.save(pregunta);
    }

    @Override
    public Pregunta actualizar(Long id, Pregunta pregunta) {
        return preguntaRepository.findById(id)
                .map(existing -> {
                    existing.setEnunciado(pregunta.getEnunciado());
                    existing.setPuntaje(pregunta.getPuntaje());
                    existing.setDificultad(pregunta.getDificultad());
                    if (pregunta.getTematica() != null) {
                        existing.setTematica(pregunta.getTematica());
                    }
                    return preguntaRepository.save(existing);
                })
                .orElseThrow(() -> new PreguntaNoEncontradaException(id));
    }

    @Override
    public void eliminarPorId(Long id) {
        if (!preguntaRepository.existsById(id)) {
            throw new PreguntaNoEncontradaException(id);
        }
        preguntaRepository.deleteById(id);
    }

    @Override
    public List<Pregunta> buscarPorEnunciado(String enunciado) {
        return preguntaRepository.findByEnunciadoContainingIgnoreCase(enunciado);
    }

    @Override
    public List<Pregunta> buscarPorRangoDePuntaje(Double puntajeMin, Double puntajeMax) {
        return preguntaRepository.findByPuntajeBetween(puntajeMin, puntajeMax);
    }

    @Override
    public Page<Pregunta> buscarPorFiltros(Long tematicaId, String tipo, Pageable pageable) {
        if (tematicaId != null && tipo != null && !tipo.isBlank()) {
            return preguntaRepository.findByTematicaIdAndTipo(tematicaId, tipo, pageable);
        } else if (tematicaId != null) {
            return preguntaRepository.findByTematicaId(tematicaId, pageable);
        } else if (tipo != null && !tipo.isBlank()) {
            return preguntaRepository.findByTipo(tipo, pageable);
        }
        return preguntaRepository.findAll(pageable);
    }
}