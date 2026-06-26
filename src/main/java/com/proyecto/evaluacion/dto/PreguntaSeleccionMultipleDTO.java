package com.proyecto.evaluacion.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaSeleccionMultipleDTO extends PreguntaDTO {

    private String opciones;
    private String correctas;
}