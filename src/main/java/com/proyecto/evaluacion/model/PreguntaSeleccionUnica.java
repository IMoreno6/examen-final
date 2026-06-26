package com.proyecto.evaluacion.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SELECCION_UNICA")
public class PreguntaSeleccionUnica extends Pregunta {

    private String opciones;
    private String correcta;

    public PreguntaSeleccionUnica() {
    }

    public PreguntaSeleccionUnica(Long id, String enunciado, Double puntaje, Integer dificultad, Tematica tematica, String opciones, String correcta) {
        super(id, enunciado, puntaje, dificultad, tematica);
        this.opciones = opciones;
        this.correcta = correcta;
    }

    public String getOpciones() { return opciones; }
    public void setOpciones(String opciones) { this.opciones = opciones; }
    public String getCorrecta() { return correcta; }
    public void setCorrecta(String correcta) { this.correcta = correcta; }
}