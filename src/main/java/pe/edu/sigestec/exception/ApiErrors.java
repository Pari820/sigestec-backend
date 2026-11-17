package pe.edu.sigestec.exception;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class ApiErrors {
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> negocio(NegocioException e) {
        return ResponseEntity.status(e.getStatus()).body(Map.of("mensaje", e.getMessage(), "fecha", Instant.now().toString()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> campos(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(Map.of("mensaje", e.getBindingResult().getFieldErrors().stream().map(x -> x.getField() + ": " + x.getDefaultMessage()).findFirst().orElse("Datos incorrectos")));
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<?> grande(Exception e) {
        return ResponseEntity.badRequest().body(Map.of("mensaje", "Archivo excede el tamaño permitido (8 MB por archivo)"));
    }

    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> tipo(Exception e) {
        return ResponseEntity.badRequest().body(Map.of("mensaje", "Tipo de dato inválido"));
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<?> loginIncorrecto(Exception e) {
        return ResponseEntity.status(401).body(Map.of("mensaje", "Usuario o contraseña incorrectos"));
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<?> integridad(Exception e) {
        return ResponseEntity.status(409).body(Map.of("mensaje", "El registro viola una restricción de integridad o unicidad"));
    }

}
