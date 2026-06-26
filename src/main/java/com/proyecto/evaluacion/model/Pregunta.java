package com.proyecto.evaluacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "preguntas")
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El enunciado no puede estar vacío")
    private String enunciado;

    @Size(min = 3, max = 200, message = "La descripción debe tener entre 3 y 200 caracteres")
    private String descripcion;

    @NotNull(message = "El puntaje es obligatorio")
    @Positive(message = "El puntaje debe ser mayor que 0")
    private Double puntaje;

    @Min(value = 1, message = "La dificultad mínima es 1")
    @Max(value = 5, message = "La dificultad máxima es 5")
    private Integer dificultad;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "tematica_id")
    private Tematica tematica;

    public Pregunta() {
    }

    public Pregunta(Long id, String enunciado, String descripcion, Double puntaje, Integer dificultad, String tipo, Tematica tematica) {
        this.id = id;
        this.enunciado = enunciado;
        this.descripcion = descripcion;
        this.puntaje = puntaje;
        this.dificultad = dificultad;
        this.tipo = tipo;
        this.tematica = tematica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public Integer getDificultad() {
        return dificultad;
    }

    public void setDificultad(Integer dificultad) {
        this.dificultad = dificultad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Tematica getTematica() {
        return tematica;
    }

    public void setTematica(Tematica tematica) {
        this.tematica = tematica;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", enunciado='" + enunciado + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", puntaje=" + puntaje +
                ", dificultad=" + dificultad +
                ", tipo='" + tipo + '\'' +
                ", tematica=" + tematica +
                '}';
    }
}