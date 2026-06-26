package com.proyecto.evaluacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaDTO {

    private Long id;
    private String enunciado;
    private String descripcion;
    private Double puntaje;
    private Integer dificultad;
    private String tipo;
    private Long tematicaId;
    private String tematicaNombre;
}