package com.proyecto.evaluacion.service;

import com.proyecto.evaluacion.model.Tematica;

import java.util.List;
import java.util.Optional;

public interface TematicaService {

    List<Tematica> listarTodas();

    Optional<Tematica> buscarPorId(Long id);

    Tematica guardar(Tematica tematica);

    void eliminarPorId(Long id);
}