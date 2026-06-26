package com.proyecto.evaluacion.controller;

import com.proyecto.evaluacion.model.Pregunta;
import com.proyecto.evaluacion.service.TematicaService;
import com.proyecto.evaluacion.service.PreguntaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/preguntas")
public class PreguntaController {

    private final PreguntaService preguntaService;
    private final TematicaService tematicaService;

    public PreguntaController(PreguntaService preguntaService, TematicaService tematicaService) {
        this.preguntaService = preguntaService;
        this.tematicaService = tematicaService;
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
        model.addAttribute("pregunta", new Pregunta());
        model.addAttribute("tematicas", tematicaService.listarTodas());
        return "formulario-pregunta";
    }

    @PostMapping("/guardar")
    public String guardarPregunta(@Valid @ModelAttribute Pregunta pregunta, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tematicas", tematicaService.listarTodas());
            return "formulario-pregunta";
        }
        preguntaService.guardar(pregunta);
        return "redirect:/preguntas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("pregunta", preguntaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada: " + id)));
        model.addAttribute("tematicas", tematicaService.listarTodas());
        return "formulario-pregunta";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPregunta(@PathVariable Long id) {
        preguntaService.eliminarPorId(id);
        return "redirect:/preguntas";
    }
}