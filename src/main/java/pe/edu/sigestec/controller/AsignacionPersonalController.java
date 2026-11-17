package pe.edu.sigestec.controller;

import pe.edu.sigestec.dto.Forms;
import pe.edu.sigestec.service.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/ordenes-trabajo")
public class AsignacionPersonalController {
    private final AsignacionPersonalService service;
    private final Vistas vistas;

    public AsignacionPersonalController(AsignacionPersonalService s, Vistas v) {
        service = s;
        vistas = v;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public Object asignar(@Valid @RequestBody Forms.AsignacionForm f) {
        return vistas.orden(service.registrar(f));
    }

    @PutMapping("/{id}/equipo")
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public Object cambiar(@PathVariable Long id, @Valid @RequestBody Forms.CambioEquipoForm f) {
        return vistas.orden(service.cambiarEquipo(id, f));
    }
}
