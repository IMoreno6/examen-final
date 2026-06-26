package com.proyecto.evaluacion.service.impl;

import com.proyecto.evaluacion.model.Tematica;
import com.proyecto.evaluacion.repository.TematicaRepository;
import com.proyecto.evaluacion.service.TematicaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TematicaServiceImpl implements TematicaService {

    private final TematicaRepository tematicaRepository;

    public TematicaServiceImpl(TematicaRepository tematicaRepository) {
        this.tematicaRepository = tematicaRepository;
    }

    @Override
    public List<Tematica> listarTodas() {
        return tematicaRepository.findAll();
    }

    @Override
    public Optional<Tematica> buscarPorId(Long id) {
        return tematicaRepository.findById(id);
    }

    @Override
    public Tematica guardar(Tematica tematica) {
        return tematicaRepository.save(tematica);
    }

    @Override
    public void eliminarPorId(Long id) {
        tematicaRepository.deleteById(id);
    }
}