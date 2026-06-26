package com.proyecto.evaluacion.controller.api;

import com.proyecto.evaluacion.dto.*;
import com.proyecto.evaluacion.exception.PreguntaNoEncontradaException;
import com.proyecto.evaluacion.model.*;
import com.proyecto.evaluacion.service.PreguntaService;
import com.proyecto.evaluacion.service.TematicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preguntas")
@Tag(name = "Preguntas", description = "API REST para la gestión de preguntas")
public class PreguntaRestController {

    private final PreguntaService preguntaService;
    private final TematicaService tematicaService;

    public PreguntaRestController(PreguntaService preguntaService, TematicaService tematicaService) {
        this.preguntaService = preguntaService;
        this.tematicaService = tematicaService;
    }

    @Operation(summary = "Listar todas las preguntas", description = "Retorna un listado paginado de preguntas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado paginado obtenido correctamente",
            content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos")
    })
    @GetMapping
    public ResponseEntity<Page<PreguntaDTO>> listarPreguntas(Pageable pageable) {
        Page<Pregunta> pagina = preguntaService.listarPaginado(pageable);
        Page<PreguntaDTO> dtoPage = pagina.map(this::toDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @Operation(summary = "Obtener pregunta por ID", description = "Retorna una pregunta según su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pregunta encontrada"),
        @ApiResponse(responseCode = "404", description = "Pregunta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PreguntaDTO> obtenerPregunta(@PathVariable Long id) {
        Pregunta pregunta = preguntaService.buscarPorId(id)
                .orElseThrow(() -> new PreguntaNoEncontradaException(id));
        return ResponseEntity.ok(toDTO(pregunta));
    }

    @Operation(summary = "Crear nueva pregunta", description = "Crea una pregunta a partir de los datos proporcionados")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pregunta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Temática no encontrada")
    })
    @PostMapping
    public ResponseEntity<PreguntaDTO> crearPregunta(@Valid @RequestBody PreguntaRequestDTO request) {
        Tematica tematica = tematicaService.buscarPorId(request.getTematicaId())
                .orElseThrow(() -> new RuntimeException("Temática no encontrada con id: " + request.getTematicaId()));

        Pregunta pregunta = toEntity(request, tematica);
        Pregunta guardada = preguntaService.guardar(pregunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(guardada));
    }

    @Operation(summary = "Actualizar pregunta", description = "Actualiza los datos de una pregunta existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pregunta actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Pregunta o Temática no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PreguntaDTO> actualizarPregunta(@PathVariable Long id,
                                                          @Valid @RequestBody PreguntaRequestDTO request) {
        Tematica tematica = tematicaService.buscarPorId(request.getTematicaId())
                .orElseThrow(() -> new RuntimeException("Temática no encontrada con id: " + request.getTematicaId()));

        Pregunta pregunta = toEntity(request, tematica);
        Pregunta actualizada = preguntaService.actualizar(id, pregunta);
        return ResponseEntity.ok(toDTO(actualizada));
    }

    @Operation(summary = "Eliminar pregunta", description = "Elimina una pregunta según su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pregunta eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Pregunta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable Long id) {
        Pregunta pregunta = preguntaService.buscarPorId(id)
                .orElseThrow(() -> new PreguntaNoEncontradaException(id));
        preguntaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    private PreguntaDTO toDTO(Pregunta pregunta) {
        PreguntaDTO dto = new PreguntaDTO();
        dto.setId(pregunta.getId());
        dto.setEnunciado(pregunta.getEnunciado());
        dto.setPuntaje(pregunta.getPuntaje());
        dto.setDificultad(pregunta.getDificultad());
        dto.setTipo(pregunta.getTipo());
        dto.setTematicaId(pregunta.getTematica().getId());
        dto.setTematicaNombre(pregunta.getTematica().getNombre());
        return dto;
    }

    private Pregunta toEntity(PreguntaRequestDTO request, Tematica tematica) {
        Pregunta pregunta;
        switch (request.getTipo()) {
            case "VF" -> {
                PreguntaVerdaderoFalso vf = new PreguntaVerdaderoFalso();
                vf.setCorrecto(request.getCorrecto());
                pregunta = vf;
            }
            case "SELECCION_UNICA" -> {
                PreguntaSeleccionUnica su = new PreguntaSeleccionUnica();
                su.setOpciones(request.getOpciones());
                su.setCorrecta(request.getCorrecta());
                pregunta = su;
            }
            case "SELECCION_MULTIPLE" -> {
                PreguntaSeleccionMultiple sm = new PreguntaSeleccionMultiple();
                sm.setOpciones(request.getOpciones());
                sm.setCorrectas(request.getCorrectas());
                pregunta = sm;
            }
            default -> throw new IllegalArgumentException("Tipo de pregunta no válido: " + request.getTipo());
        }
        pregunta.setEnunciado(request.getEnunciado());
        pregunta.setPuntaje(request.getPuntaje());
        pregunta.setDificultad(request.getDificultad());
        pregunta.setTematica(tematica);
        return pregunta;
    }
}