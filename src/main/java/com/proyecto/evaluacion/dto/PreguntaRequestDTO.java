package com.proyecto.evaluacion.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaRequestDTO {

    @NotBlank(message = "El enunciado no puede estar vacío")
    private String enunciado;

    @NotNull(message = "El puntaje es obligatorio")
    @Positive(message = "El puntaje debe ser mayor que 0")
    private Double puntaje;

    @Min(value = 1, message = "La dificultad mínima es 1")
    @Max(value = 5, message = "La dificultad máxima es 5")
    private Integer dificultad;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotNull(message = "La temática es obligatoria")
    private Long tematicaId;

    private Boolean correcto;
    private String opciones;
    private String correcta;
    private String correctas;
}