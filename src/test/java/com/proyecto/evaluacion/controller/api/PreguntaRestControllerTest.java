package com.proyecto.evaluacion.controller.api;

import com.proyecto.evaluacion.model.Pregunta;
import com.proyecto.evaluacion.model.PreguntaVerdaderoFalso;
import com.proyecto.evaluacion.model.Tematica;
import com.proyecto.evaluacion.security.JwtTokenProvider;
import com.proyecto.evaluacion.service.PreguntaService;
import com.proyecto.evaluacion.service.TematicaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = PreguntaRestController.class, excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
})
class PreguntaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PreguntaService preguntaService;

    @MockBean
    private TematicaService tematicaService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void testObtenerPregunta_ShouldReturnPregunta() throws Exception {
        Tematica tematica = new Tematica(1L, "Matematicas");
        Pregunta pregunta = new PreguntaVerdaderoFalso(1L, "2+2=4?", 1.0, 1, tematica, true);

        Mockito.when(preguntaService.buscarPorId(1L)).thenReturn(Optional.of(pregunta));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/preguntas/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.enunciado").value("2+2=4?"))
                .andExpect(jsonPath("$.puntaje").value(1.0))
                .andExpect(jsonPath("$.dificultad").value(1))
                .andExpect(jsonPath("$.tematicaId").value(1))
                .andExpect(jsonPath("$.tematicaNombre").value("Matematicas"));
    }

    @Test
    void testObtenerPregunta_NotFound() throws Exception {
        Mockito.when(preguntaService.buscarPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/preguntas/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}