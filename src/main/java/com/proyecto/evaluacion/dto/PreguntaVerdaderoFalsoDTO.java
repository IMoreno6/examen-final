package com.proyecto.evaluacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreguntaVerdaderoFalsoDTO extends PreguntaDTO {

    private Boolean correcto;
}