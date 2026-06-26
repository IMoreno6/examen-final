INSERT INTO tematicas (nombre) VALUES ('Matematicas');
INSERT INTO tematicas (nombre) VALUES ('Ciencias');
INSERT INTO tematicas (nombre) VALUES ('Historia');
INSERT INTO tematicas (nombre) VALUES ('Lenguaje');
INSERT INTO tematicas (nombre) VALUES ('Tecnologia');

INSERT INTO preguntas (enunciado, descripcion, puntaje, dificultad, tipo, tematica_id) VALUES ('Cual es la raiz cuadrada de 144?', 'Pregunta basica de matematicas', 1.0, 1, 'CORTA', 1);
INSERT INTO preguntas (enunciado, descripcion, puntaje, dificultad, tipo, tematica_id) VALUES ('Que formula representa el teorema de Pitagoras?', 'Pregunta de geometria', 2.0, 2, 'MULTIPLE', 1);
INSERT INTO preguntas (enunciado, descripcion, puntaje, dificultad, tipo, tematica_id) VALUES ('Cual es el planeta mas grande del sistema solar?', 'Pregunta de ciencias naturales', 1.5, 1, 'MULTIPLE', 2);
INSERT INTO preguntas (enunciado, descripcion, puntaje, dificultad, tipo, tematica_id) VALUES ('En que año llego el hombre a la luna?', 'Pregunta de historia contemporanea', 2.0, 2, 'CORTA', 3);
INSERT INTO preguntas (enunciado, descripcion, puntaje, dificultad, tipo, tematica_id) VALUES ('Quien escribio "Cien años de soledad"?', 'Pregunta de literatura', 3.0, 3, 'MULTIPLE', 4);
INSERT INTO preguntas (enunciado, descripcion, puntaje, dificultad, tipo, tematica_id) VALUES ('Que significa la sigla HTML?', 'Pregunta de desarrollo web', 1.0, 1, 'CORTA', 5);
INSERT INTO preguntas (enunciado, descripcion, puntaje, dificultad, tipo, tematica_id) VALUES ('Cual es la derivada de x^2?', 'Pregunta de calculo diferencial', 4.0, 4, 'DESARROLLO', 1);
INSERT INTO preguntas (enunciado, descripcion, puntaje, dificultad, tipo, tematica_id) VALUES ('La Tierra es plana?', 'Cultura general', 1.0, 1, 'VF', 2);
INSERT INTO preguntas (enunciado, descripcion, puntaje, dificultad, tipo, tematica_id) VALUES ('Cual es la capital de Francia?', 'Cultura general', 1.0, 1, 'CORTA', 3);
INSERT INTO preguntas (enunciado, descripcion, puntaje, dificultad, tipo, tematica_id) VALUES ('Explica el ciclo del agua', 'Pregunta de ciencias', 5.0, 4, 'DESARROLLO', 2);