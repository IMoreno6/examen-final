package com.proyecto.evaluacion.exception;

import com.proyecto.evaluacion.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PreguntaNoEncontradaException.class)
    public ResponseEntity<ErrorResponseDTO> handlePreguntaNoEncontrada(PreguntaNoEncontradaException ex, WebRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO("NOT_FOUND", ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponseDTO error = new ErrorResponseDTO("VALIDATION_ERROR", mensaje, request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(NoHandlerFoundException ex, WebRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO("NOT_FOUND", "Recurso no encontrado", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneral(Exception ex, WebRequest request) {
        ErrorResponseDTO error = new ErrorResponseDTO("INTERNAL_ERROR", "Error interno del servidor", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}