package pe.edu.sigestec.controller;

import pe.edu.sigestec.dto.Forms;
import pe.edu.sigestec.repository.CotizacionRepository;
import pe.edu.sigestec.service.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/cotizaciones")
public class CotizacionController {
    private final CotizacionRepository repo;
    private final CotizacionService service;
    private final Vistas vistas;

    public CotizacionController(CotizacionRepository r, CotizacionService s, Vistas v) {
        repo = r;
        service = s;
        vistas = v;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','SUPERVISOR','ADMIN')")
    public List<?> listar() {
        return repo.findAll().stream().map(vistas::cotizacion).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public Object registrar(@Valid @RequestBody Forms.CotizacionForm f) {
        return vistas.cotizacion(service.registrar(f));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public Object registrarAdjunto(@Valid @RequestPart("datos") Forms.CotizacionForm f,
                                   @RequestPart(value = "pdf", required = false) MultipartFile pdf) {
        return vistas.cotizacion(service.registrar(f, pdf));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public Object editar(@PathVariable Long id, @Valid @RequestBody Forms.CotizacionForm f) {
        return vistas.cotizacion(service.editar(id, f));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public Object editarAdjunto(@PathVariable Long id, @Valid @RequestPart("datos") Forms.CotizacionForm f,
                                @RequestPart(value = "pdf", required = false) MultipartFile pdf) {
        return vistas.cotizacion(service.editar(id, f, pdf));
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public Object estado(@PathVariable Long id, @Valid @RequestBody Forms.EstadoForm f) {
        return vistas.cotizacion(service.cambiarEstado(id, f.estado()));
    }
}
