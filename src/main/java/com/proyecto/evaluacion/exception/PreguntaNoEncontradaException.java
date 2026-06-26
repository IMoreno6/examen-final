package com.proyecto.evaluacion.exception;

public class PreguntaNoEncontradaException extends RuntimeException {

    public PreguntaNoEncontradaException(Long id) {
        super("Pregunta no encontrada con id: " + id);
    }

    public PreguntaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}