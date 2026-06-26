package com.proyecto.evaluacion.repository;

import com.proyecto.evaluacion.model.Tematica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TematicaRepository extends JpaRepository<Tematica, Long> {

}