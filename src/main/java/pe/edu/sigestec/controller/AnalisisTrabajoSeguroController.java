package pe.edu.sigestec.controller;

import pe.edu.sigestec.dto.Forms;
import pe.edu.sigestec.repository.AnalisisTrabajoSeguroRepository;
import pe.edu.sigestec.service.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

/**
 * Solo CU06. No implementa la aprobación (CU07).
 */
@RestController
@RequestMapping("/api/v1/ats")
public class AnalisisTrabajoSeguroController {
    private final AnalisisTrabajoSeguroRepository repo;
    private final AnalisisTrabajoSeguroService service;
    private final Vistas vistas;

    public AnalisisTrabajoSeguroController(AnalisisTrabajoSeguroRepository r, AnalisisTrabajoSeguroService s, Vistas v) {
        repo = r;
        service = s;
        vistas = v;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SSOMA','SUPERVISOR','ADMIN')")
    public List<?> listar() {
        return repo.findAll().stream().map(vistas::analisisTrabajoSeguro).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SSOMA','ADMIN')")
    public Object elaborar(@Valid @RequestBody Forms.AtsForm f) {
        return vistas.analisisTrabajoSeguro(service.elaborar(f));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('SSOMA','ADMIN')")
    public Object elaborarAdjunto(@Valid @RequestPart("datos") Forms.AtsForm f,
                                  @RequestPart(value = "pdf", required = false) MultipartFile pdf) {
        return vistas.analisisTrabajoSeguro(service.elaborar(f, pdf));
    }
}
