package pe.edu.sigestec.controller;

import pe.edu.sigestec.service.*;
import pe.edu.sigestec.repository.OrdenTrabajoRepository;
import pe.edu.sigestec.entity.DocumentoPlanificacion;
import pe.edu.sigestec.storage.ArchivoStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/ordenes-trabajo")
public class OrdenTrabajoController {
    private final OrdenTrabajoRepository repo;
    private final OrdenTrabajoService service;
    private final Vistas vistas;
    private final ArchivoStorageService storage;

    public OrdenTrabajoController(OrdenTrabajoRepository r, OrdenTrabajoService s, Vistas v, ArchivoStorageService st) {
        repo = r;
        service = s;
        vistas = v;
        storage = st;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERVISOR','SSOMA','ALMACENERO','ADMIN')")
    public List<?> listar() {
        return repo.findAll().stream().map(vistas::orden).toList();
    }

    @PostMapping(value = "/{id}/programacion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public Object programar(@PathVariable Long id,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
                            @RequestParam String lugar,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEnvio,
                            @RequestParam boolean aprobadoCliente,
                            @RequestPart(value = "pt", required = false) MultipartFile pt,
                            @RequestPart(value = "iper", required = false) MultipartFile iper,
                            @RequestPart(value = "croquis", required = false) MultipartFile croquis,
                            @RequestParam(required = false) String motivoCambio) {
        return vistas.orden(service.programar(id, inicio, fin, lugar, fechaEnvio, aprobadoCliente, pt, iper, croquis, motivoCambio));
    }

    @GetMapping("/{id}/documentos/{tipo}")
    @PreAuthorize("hasAnyRole('SUPERVISOR','SSOMA','ADMIN')")
    public ResponseEntity<Resource> documento(@PathVariable Long id, @PathVariable String tipo) {
        DocumentoPlanificacion d = service.documento(id, tipo);
        String nombre = d.getNombreDocumento().replaceAll("[\\r\\n\"]", "_");
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(storage.tipoContenido(d.getUrlArchivo())))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombre + "\"")
                .body(storage.cargar(d.getUrlArchivo()));
    }
}
