# Evaluación - Proyecto Spring Boot

Aplicación Spring Boot 3.1.0 (Java 17) para gestión de preguntas de evaluación con autenticación JWT, persistencia JPA y documentación Swagger.

---

## Requisitos previos

- **Java 17** (JDK)
- **Maven 3.8+**
- **Docker** y **Docker Compose** (solo para el despliegue con Docker)
- Opcional: **MySQL 8.0** (para perfil `prod` fuera de Docker)

---

## Arrancar en local (perfil dev — H2 en memoria)

El perfil por defecto (`dev`) usa una base de datos **H2 en memoria**, sin necesidad de instalar MySQL.

```bash
# Compilar y ejecutar
mvn clean package -DskipTests
java -jar target/evaluacion-0.0.1-SNAPSHOT.jar
```

O directamente con Maven:

```bash
mvn spring-boot:run
```

La aplicación arrancará en `http://localhost:8080`.

### Recursos disponibles

| Recurso               | URL                                 |
|-----------------------|-------------------------------------|
| Web (Thymeleaf)       | `http://localhost:8080/`            |
| API REST              | `http://localhost:8080/api/`        |
| Swagger UI            | `http://localhost:8080/swagger-ui.html` |
| Consola H2            | `http://localhost:8080/h2-console`  |
|                        | JDBC URL: `jdbc:h2:mem:testdb`      |
|                        | User: `sa` / Password: *(vacío)*    |

---

## Arrancar en local con MySQL (perfil prod)

Requiere una instancia de **MySQL 8.0** corriendo en `localhost:3306`.

```bash
# Crear la base de datos
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS preguntas_db;"

# Compilar
mvn clean package -DskipTests

# Ejecutar con perfil prod
java -Dspring.profiles.active=prod -jar target/evaluacion-0.0.1-SNAPSHOT.jar
```

Variables de entorno configurables (con valores por defecto):

| Variable       | Valor por defecto |
|----------------|-------------------|
| `DB_HOST`      | `localhost`       |
| `DB_PORT`      | `3306`            |
| `DB_NAME`      | `preguntas_db`    |
| `DB_USER`      | `root`            |
| `DB_PASSWORD`  | `root`            |

---

## Arrancar con Docker

### Construir y ejecutar (app + MySQL)

```bash
docker-compose up --build
```

Esto levanta dos contenedores:
- **db**: MySQL 8.0 en `puerto 3306`
- **app**: La aplicación Spring Boot en `puerto 8080`

### Ejecutar en segundo plano

```bash
docker-compose up --build -d
```

### Detener y limpiar

```bash
docker-compose down -v
```

El flag `-v` elimina también el volumen de MySQL.

---

## Ejecutar tests

```bash
mvn test
```

---

## Construir imagen Docker manualmente

```bash
docker build -t evaluacion-app .
docker run -p 8080:8080 --name evaluacion-app evaluacion-app
```

> Nota: la imagen usa el perfil `prod` por defecto, por lo que necesitará un contenedor MySQL accesible o bien sobreescribir las variables de entorno.

---

## Estructura del proyecto

```
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/proyecto/evaluacion/
    │   │   ├── config/           # Configuraciones (seguridad, datos iniciales)
    │   │   ├── controller/       # Controladores web y REST
    │   │   ├── dto/              # Objetos de transferencia de datos
    │   │   ├── exception/        # Manejo de excepciones
    │   │   ├── model/            # Entidades JPA
    │   │   ├── repository/       # Repositorios Spring Data
    │   │   ├── security/         # JWT y seguridad
    │   │   └── service/          # Lógica de negocio
    │   └── resources/
    │       ├── application.properties
    │       ├── application-dev.properties   # H2 en memoria
    │       └── application-prod.properties  # MySQL
    └── test/
        └── java/com/proyecto/evaluacion/
            ├── EvaluacionApplicationTests.java
            ├── controller/api/
            └── service/
```
