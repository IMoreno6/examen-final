package com.proyecto.evaluacion;

import com.proyecto.evaluacion.model.Pregunta;
import com.proyecto.evaluacion.model.PreguntaVerdaderoFalso;
import com.proyecto.evaluacion.model.Tematica;
import com.proyecto.evaluacion.repository.PreguntaRepository;
import com.proyecto.evaluacion.repository.TematicaRepository;
import com.proyecto.evaluacion.service.PreguntaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
class EvaluacionApplicationTests {

    @Autowired
    private PreguntaService preguntaService;

    @Autowired
    private TematicaRepository tematicaRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Test
    void contextLoads() {}

    @Test
    void deInicializarDatos() {
        List<Tematica> tematicas = tematicaRepository.findAll();
        assertThat(tematicas).hasSize(5);
    }

    @Test
    void deListarPreguntasPaginadas() {
        Page<Pregunta> pagina = preguntaService.listarPaginado(PageRequest.of(0, 5));
        assertThat(pagina.getContent()).isNotEmpty();
        assertThat(pagina.getTotalElements()).isEqualTo(9);
    }

    @Test
    void deBuscarPreguntaPorId() {
        List<Pregunta> todas = preguntaRepository.findAll();
        Optional<Pregunta> resultado = preguntaService.buscarPorId(todas.get(0).getId());
        assertThat(resultado).isPresent();
    }

    @Test
    void deGuardarPregunta() {
        Tematica tematica = tematicaRepository.findAll().get(0);
        Pregunta pregunta = new PreguntaVerdaderoFalso(null, "Test?", 1.0, 1, tematica, true);
        Pregunta guardada = preguntaService.guardar(pregunta);
        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getEnunciado()).isEqualTo("Test?");
    }

    @Test
    void deEliminarPregunta() {
        List<Pregunta> todas = preguntaRepository.findAll();
        long count = preguntaRepository.count();
        preguntaService.eliminarPorId(todas.get(0).getId());
        assertThat(preguntaRepository.count()).isEqualTo(count - 1);
    }

    @Test
    void deBuscarPorFiltros() {
        Long tematicaId = tematicaRepository.findAll().get(0).getId();
        Page<Pregunta> pagina = preguntaService.buscarPorFiltros(tematicaId, null, PageRequest.of(0, 10));
        assertThat(pagina.getContent()).allMatch(p -> p.getTematica().getId().equals(tematicaId));
    }
}