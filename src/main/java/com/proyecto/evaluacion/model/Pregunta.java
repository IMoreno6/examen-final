package com.proyecto.evaluacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "preguntas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El enunciado no puede estar vacío")
    private String enunciado;

    @NotNull(message = "El puntaje es obligatorio")
    @Positive(message = "El puntaje debe ser mayor que 0")
    private Double puntaje;

    @Min(value = 1, message = "La dificultad mínima es 1")
    @Max(value = 5, message = "La dificultad máxima es 5")
    private Integer dificultad;

    @ManyToOne
    @JoinColumn(name = "tematica_id")
    private Tematica tematica;

    protected Pregunta() {
    }

    protected Pregunta(Long id, String enunciado, Double puntaje, Integer dificultad, Tematica tematica) {
        this.id = id;
        this.enunciado = enunciado;
        this.puntaje = puntaje;
        this.dificultad = dificultad;
        this.tematica = tematica;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    public Double getPuntaje() { return puntaje; }
    public void setPuntaje(Double puntaje) { this.puntaje = puntaje; }
    public Integer getDificultad() { return dificultad; }
    public void setDificultad(Integer dificultad) { this.dificultad = dificultad; }
    public Tematica getTematica() { return tematica; }
    public void setTematica(Tematica tematica) { this.tematica = tematica; }

    @Transient
    public String getTipo() {
        DiscriminatorValue dv = getClass().getAnnotation(DiscriminatorValue.class);
        return dv != null ? dv.value() : null;
    }

    @Override
    public String toString() {
        return "Pregunta{" + "id=" + id + ", enunciado='" + enunciado + '\'' + '}';
    }
}