package com.proyecto.evaluacion.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SELECCION_MULTIPLE")
public class PreguntaSeleccionMultiple extends Pregunta {

    private String opciones;
    private String correctas;

    public PreguntaSeleccionMultiple() {
    }

    public PreguntaSeleccionMultiple(Long id, String enunciado, Double puntaje, Integer dificultad, Tematica tematica, String opciones, String correctas) {
        super(id, enunciado, puntaje, dificultad, tematica);
        this.opciones = opciones;
        this.correctas = correctas;
    }

    public String getOpciones() { return opciones; }
    public void setOpciones(String opciones) { this.opciones = opciones; }
    public String getCorrectas() { return correctas; }
    public void setCorrectas(String correctas) { this.correctas = correctas; }
}