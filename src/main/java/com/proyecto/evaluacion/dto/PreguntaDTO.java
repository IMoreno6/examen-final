package com.proyecto.evaluacion.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaDTO {
    private Long id;
    private String enunciado;
    private Double puntaje;
    private Integer dificultad;
    private String tipo;
    private Long tematicaId;
    private String tematicaNombre;
}