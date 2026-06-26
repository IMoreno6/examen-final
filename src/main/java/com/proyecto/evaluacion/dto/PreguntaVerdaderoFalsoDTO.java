package com.proyecto.evaluacion.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaVerdaderoFalsoDTO extends PreguntaDTO {

    private Boolean correcto;
}