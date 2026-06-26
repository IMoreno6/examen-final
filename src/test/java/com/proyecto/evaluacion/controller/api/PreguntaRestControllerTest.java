package com.proyecto.evaluacion.controller.api;

import com.proyecto.evaluacion.dto.PreguntaRequestDTO;
import com.proyecto.evaluacion.model.Tematica;
import com.proyecto.evaluacion.repository.TematicaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class PreguntaRestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TematicaRepository tematicaRepository;

    @LocalServerPort
    private int port;

    private String adminAuth;

    @BeforeEach
    void setUp() {
        adminAuth = "Basic " + java.util.Base64.getEncoder().encodeToString("admin:admin123".getBytes());
    }

    @Test
    void deListarPreguntas() {
        var headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", adminAuth);
        var entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/preguntas", HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deCrearPregunta() {
        Tematica tematica = tematicaRepository.findAll().get(0);
        PreguntaRequestDTO request = PreguntaRequestDTO.builder()
                .enunciado("Pregunta de test?")
                .puntaje(2.0)
                .dificultad(3)
                .tipo("SELECCION_UNICA")
                .tematicaId(tematica.getId())
                .opciones("A, B, C")
                .correcta("A")
                .build();

        var headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", adminAuth);
        var entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/preguntas", HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void deRechazarNoAutenticado() throws Exception {
        URL url = new URL("http://localhost:" + port + "/api/preguntas");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setInstanceFollowRedirects(false);
        int status = conn.getResponseCode();
        conn.disconnect();

        assertThat(status).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}