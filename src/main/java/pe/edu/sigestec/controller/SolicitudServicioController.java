package pe.edu.sigestec.controller;

import pe.edu.sigestec.dto.Forms;
import pe.edu.sigestec.repository.SolicitudServicioRepository;
import pe.edu.sigestec.service.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/solicitudes")
public class SolicitudServicioController {
    private final SolicitudServicioRepository repo;
    private final SolicitudServicioService service;
    private final Vistas vistas;

    public SolicitudServicioController(SolicitudServicioRepository r, SolicitudServicioService s, Vistas v) {
        repo = r;
        service = s;
        vistas = v;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','SUPERVISOR','SSOMA','ADMIN')")
    public List<?> listar() {
        return repo.findAll().stream().map(vistas::solicitud).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public Object registrar(@Valid @RequestBody Forms.SolicitudForm f) {
        return vistas.solicitud(service.registrar(f));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public Object registrarAdjunto(@Valid @RequestPart("datos") Forms.SolicitudForm f,
                                   @RequestPart(value = "pdf", required = false) MultipartFile pdf) {
        return vistas.solicitud(service.registrar(f, pdf));
    }
}
