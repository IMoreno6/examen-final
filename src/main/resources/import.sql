INSERT INTO tematicas (nombre) VALUES ('Matematicas');
INSERT INTO tematicas (nombre) VALUES ('Ciencias');
INSERT INTO tematicas (nombre) VALUES ('Historia');
INSERT INTO tematicas (nombre) VALUES ('Lenguaje');
INSERT INTO tematicas (nombre) VALUES ('Tecnologia');

INSERT INTO preguntas (tipo, enunciado, puntaje, dificultad, tematica_id, correcto) VALUES ('VF', 'El agua hierve a 100 grados Celsius', 1.0, 1, 2, true);
INSERT INTO preguntas (tipo, enunciado, puntaje, dificultad, tematica_id, correcto) VALUES ('VF', 'La Tierra es plana', 1.0, 1, 2, false);
INSERT INTO preguntas (tipo, enunciado, puntaje, dificultad, tematica_id, correcto) VALUES ('VF', 'El sol es una estrella', 1.0, 1, 2, true);

INSERT INTO preguntas (tipo, enunciado, puntaje, dificultad, tematica_id, opciones, correcta) VALUES ('SELECCION_UNICA', 'Cual es la raiz cuadrada de 144?', 1.0, 1, 1, '10, 11, 12, 13', '12');
INSERT INTO preguntas (tipo, enunciado, puntaje, dificultad, tematica_id, opciones, correcta) VALUES ('SELECCION_UNICA', 'Cual es la capital de Francia?', 1.0, 1, 3, 'Londres, Paris, Berlin, Madrid', 'Paris');
INSERT INTO preguntas (tipo, enunciado, puntaje, dificultad, tematica_id, opciones, correcta) VALUES ('SELECCION_UNICA', 'Cual es el planeta mas grande del sistema solar?', 1.5, 1, 2, 'Marte, Jupiter, Saturno, Venus', 'Jupiter');

INSERT INTO preguntas (tipo, enunciado, puntaje, dificultad, tematica_id, opciones, correctas) VALUES ('SELECCION_MULTIPLE', 'Que lenguajes son orientados a objetos?', 2.0, 3, 5, 'Java, C, Python, HTML', 'Java, Python');
INSERT INTO preguntas (tipo, enunciado, puntaje, dificultad, tematica_id, opciones, correctas) VALUES ('SELECCION_MULTIPLE', 'Cuales son numeros primos?', 2.0, 3, 1, '2, 4, 7, 9', '2, 7');
INSERT INTO preguntas (tipo, enunciado, puntaje, dificultad, tematica_id, opciones, correctas) VALUES ('SELECCION_MULTIPLE', 'Que elementos son metales?', 2.0, 3, 2, 'Hierro, Oxigeno, Oro, Helio', 'Hierro, Oro');