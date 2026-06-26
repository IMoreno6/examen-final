package com.proyecto.evaluacion.controller.api;

import com.proyecto.evaluacion.dto.PreguntaDTO;
import com.proyecto.evaluacion.dto.PreguntaRequestDTO;
import com.proyecto.evaluacion.model.Pregunta;
import com.proyecto.evaluacion.model.Tematica;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return preguntaService.buscarPorId(id)
                .map(p -> ResponseEntity.ok(toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
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
        pregunta.setId(id);
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
        if (preguntaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        preguntaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    private PreguntaDTO toDTO(Pregunta pregunta) {
        return PreguntaDTO.builder()
                .id(pregunta.getId())
                .enunciado(pregunta.getEnunciado())
                .descripcion(pregunta.getDescripcion())
                .puntaje(pregunta.getPuntaje())
                .dificultad(pregunta.getDificultad())
                .tipo(pregunta.getTipo())
                .tematicaId(pregunta.getTematica().getId())
                .tematicaNombre(pregunta.getTematica().getNombre())
                .build();
    }

    private Pregunta toEntity(PreguntaRequestDTO request, Tematica tematica) {
        Pregunta pregunta = new Pregunta();
        pregunta.setEnunciado(request.getEnunciado());
        pregunta.setDescripcion(request.getDescripcion());
        pregunta.setPuntaje(request.getPuntaje());
        pregunta.setDificultad(request.getDificultad());
        pregunta.setTipo(request.getTipo());
        pregunta.setTematica(tematica);
        return pregunta;
    }
}