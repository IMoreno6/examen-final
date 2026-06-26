package com.proyecto.evaluacion.config;

import com.proyecto.evaluacion.model.Pregunta;
import com.proyecto.evaluacion.model.PreguntaSeleccionMultiple;
import com.proyecto.evaluacion.model.PreguntaSeleccionUnica;
import com.proyecto.evaluacion.model.PreguntaVerdaderoFalso;
import com.proyecto.evaluacion.model.Tematica;
import com.proyecto.evaluacion.repository.PreguntaRepository;
import com.proyecto.evaluacion.repository.TematicaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TematicaRepository tematicaRepository;
    private final PreguntaRepository preguntaRepository;

    public DataInitializer(TematicaRepository tematicaRepository, PreguntaRepository preguntaRepository) {
        this.tematicaRepository = tematicaRepository;
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public void run(String... args) {
        if (tematicaRepository.count() > 0) {
            return;
        }

        Tematica matematicas = tematicaRepository.save(new Tematica(null, "Matematicas"));
        Tematica ciencias = tematicaRepository.save(new Tematica(null, "Ciencias"));
        Tematica historia = tematicaRepository.save(new Tematica(null, "Historia"));
        Tematica lenguaje = tematicaRepository.save(new Tematica(null, "Lenguaje"));
        Tematica tecnologia = tematicaRepository.save(new Tematica(null, "Tecnologia"));

        preguntaRepository.save(new PreguntaVerdaderoFalso(null, "El agua hierve a 100 grados Celsius", 1.0, 1, ciencias, true));
        preguntaRepository.save(new PreguntaVerdaderoFalso(null, "La Tierra es plana", 1.0, 1, ciencias, false));
        preguntaRepository.save(new PreguntaVerdaderoFalso(null, "El sol es una estrella", 1.0, 1, ciencias, true));
        preguntaRepository.save(new PreguntaSeleccionUnica(null, "Cual es la raiz cuadrada de 144?", 1.0, 1, matematicas, "10, 11, 12, 13", "12"));
        preguntaRepository.save(new PreguntaSeleccionUnica(null, "Cual es la capital de Francia?", 1.0, 1, historia, "Londres, Paris, Berlin, Madrid", "Paris"));
        preguntaRepository.save(new PreguntaSeleccionUnica(null, "Cual es el planeta mas grande del sistema solar?", 1.0, 1, ciencias, "Marte, Jupiter, Saturno, Venus", "Jupiter"));
        preguntaRepository.save(new PreguntaSeleccionMultiple(null, "Que lenguajes son orientados a objetos?", 2.0, 3, tecnologia, "Java, C, Python, HTML", "Java, Python"));
        preguntaRepository.save(new PreguntaSeleccionMultiple(null, "Que elementos son metales?", 2.0, 3, ciencias, "Hierro, Oxigeno, Oro, Helio", "Hierro, Oro"));
        preguntaRepository.save(new PreguntaSeleccionMultiple(null, "Cuales son numeros primos?", 2.0, 3, matematicas, "2, 4, 7, 9, 10", "2, 7"));
    }
}