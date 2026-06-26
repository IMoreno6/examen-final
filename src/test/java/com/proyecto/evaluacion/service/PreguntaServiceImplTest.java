package com.proyecto.evaluacion.service;

import com.proyecto.evaluacion.model.Pregunta;
import com.proyecto.evaluacion.model.PreguntaVerdaderoFalso;
import com.proyecto.evaluacion.model.Tematica;
import com.proyecto.evaluacion.repository.PreguntaRepository;
import com.proyecto.evaluacion.service.impl.PreguntaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PreguntaServiceImplTest {

    @Mock
    private PreguntaRepository preguntaRepository;

    @InjectMocks
    private PreguntaServiceImpl preguntaService;

    @Test
    void testFindAll_ShouldReturnList() {
        Tematica tematica = new Tematica(1L, "Matematicas");
        List<Pregunta> preguntas = Arrays.asList(
                new PreguntaVerdaderoFalso(1L, "2+2=4?", 1.0, 1, tematica, true),
                new PreguntaVerdaderoFalso(2L, "3+3=6?", 1.0, 1, tematica, true)
        );
        Mockito.when(preguntaRepository.findAll()).thenReturn(preguntas);

        List<Pregunta> resultado = preguntaService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("2+2=4?", resultado.get(0).getEnunciado());
        Mockito.verify(preguntaRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testFindById_ShouldReturnPregunta() {
        Tematica tematica = new Tematica(1L, "Ciencias");
        Pregunta pregunta = new PreguntaVerdaderoFalso(1L, "El cielo es azul?", 2.0, 2, tematica, true);
        Mockito.when(preguntaRepository.findById(1L)).thenReturn(Optional.of(pregunta));

        Optional<Pregunta> resultado = preguntaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("El cielo es azul?", resultado.get().getEnunciado());
        assertEquals(2.0, resultado.get().getPuntaje());
        Mockito.verify(preguntaRepository, Mockito.times(1)).findById(1L);
    }
}