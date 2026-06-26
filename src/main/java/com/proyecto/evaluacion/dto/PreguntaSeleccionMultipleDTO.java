package com.proyecto.evaluacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaSeleccionMultipleDTO extends PreguntaDTO {

    private String opciones;
    private String correctas;
}