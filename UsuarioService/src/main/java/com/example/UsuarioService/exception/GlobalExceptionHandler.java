package com.example.UsuarioService.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        String message = ex.getMostSpecificCause().getMessage();

        if (message.contains("Duplicate entry")) {
            if (message.contains("rut")) {
                response.put("error", "El RUT ya está registrado.");
            } else if (message.contains("email")) {
                response.put("error", "El email ya está registrado.");
            } else if (message.contains("username")) {
                response.put("error", "El nombre de usuario ya está registrado.");
            } else {
                response.put("error", "Ya existe un registro con estos datos únicos.");
            }
            response.put("detail", message);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        response.put("error", "Error de integridad de datos.");
        response.put("detail", message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Ocurrió un error interno en el servidor.");
        response.put("detail", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
