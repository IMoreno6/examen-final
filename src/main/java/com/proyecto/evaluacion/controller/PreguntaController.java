package com.proyecto.evaluacion.controller;

import com.proyecto.evaluacion.model.*;
import com.proyecto.evaluacion.service.TematicaService;
import com.proyecto.evaluacion.service.PreguntaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.beans.PropertyEditorSupport;

@Controller
@RequestMapping("/preguntas")
public class PreguntaController {

    private final PreguntaService preguntaService;
    private final TematicaService tematicaService;

    public PreguntaController(PreguntaService preguntaService, TematicaService tematicaService) {
        this.preguntaService = preguntaService;
        this.tematicaService = tematicaService;
    }

    @InitBinder
    public void initBinder(org.springframework.web.bind.WebDataBinder binder) {
        binder.registerCustomEditor(Tematica.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.isBlank()) {
                    setValue(null);
                } else {
                    Long id = Long.parseLong(text);
                    tematicaService.buscarPorId(id).ifPresent(this::setValue);
                }
            }
        });
    }

    @GetMapping
    public String listarPreguntas(
            @RequestParam(required = false) Long tematicaId,
            @RequestParam(required = false) String tipo,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        Page<Pregunta> pagina = preguntaService.buscarPorFiltros(tematicaId, tipo, PageRequest.of(page, 5));
        model.addAttribute("preguntas", pagina);
        model.addAttribute("tematicas", tematicaService.listarTodas());
        model.addAttribute("tematicaIdSel", tematicaId);
        model.addAttribute("tipoSel", tipo);
        return "lista-preguntas";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("pregunta", new PreguntaVerdaderoFalso());
        model.addAttribute("tematicas", tematicaService.listarTodas());
        return "formulario-pregunta";
    }

    @PostMapping("/guardar")
    public String guardarPregunta(@Valid @ModelAttribute("pregunta") Pregunta pregunta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tematicas", tematicaService.listarTodas());
            return "formulario-pregunta";
        }

        if (pregunta.getId() != null) {
            preguntaService.actualizar(pregunta.getId(), pregunta);
        } else {
            preguntaService.guardar(pregunta);
        }
        return "redirect:/preguntas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Pregunta pregunta = preguntaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada: " + id));
        model.addAttribute("pregunta", pregunta);
        model.addAttribute("tematicas", tematicaService.listarTodas());
        return "formulario-pregunta";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPregunta(@PathVariable Long id) {
        preguntaService.eliminarPorId(id);
        return "redirect:/preguntas";
    }
}