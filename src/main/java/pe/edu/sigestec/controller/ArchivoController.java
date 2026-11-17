package pe.edu.sigestec.controller;

import pe.edu.sigestec.storage.ArchivoStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.core.io.Resource;

/**
 * Endpoint auxiliar de descarga segura; no representa un CU nuevo.
 */
@RestController
@RequestMapping("/api/v1/archivos")
public class ArchivoController {
    private final ArchivoStorageService storage;

    public ArchivoController(ArchivoStorageService storage) {
        this.storage = storage;
    }

    @GetMapping("/{nombre:.+}")
    public ResponseEntity<Resource> descargar(@PathVariable String nombre) {
        String url = "/api/v1/archivos/" + nombre;
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(storage.tipoContenido(url)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombre + "\"")
                .body(storage.cargar(url));
    }
}
