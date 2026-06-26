package com.proyecto.evaluacion.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VF")
public class PreguntaVerdaderoFalso extends Pregunta {

    private Boolean correcto;

    public PreguntaVerdaderoFalso() {
    }

    public PreguntaVerdaderoFalso(Long id, String enunciado, Double puntaje, Integer dificultad, Tematica tematica, Boolean correcto) {
        super(id, enunciado, puntaje, dificultad, tematica);
        this.correcto = correcto;
    }

    public Boolean getCorrecto() { return correcto; }
    public void setCorrecto(Boolean correcto) { this.correcto = correcto; }
}