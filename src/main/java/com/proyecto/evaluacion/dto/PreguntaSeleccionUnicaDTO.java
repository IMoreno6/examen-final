package com.proyecto.evaluacion.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaSeleccionUnicaDTO extends PreguntaDTO {

    private String opciones;
    private String correcta;
}