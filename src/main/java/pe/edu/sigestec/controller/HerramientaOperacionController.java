package pe.edu.sigestec.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.exception.NegocioException;
import pe.edu.sigestec.service.*;

/** Alias REST compatibles con los mensajes del diagrama de secuencia CU12. */
@RestController
@RequestMapping("/api/v1/herramientas")
@PreAuthorize("hasAnyRole('ALMACENERO','ADMIN')")
public class HerramientaOperacionController {
    private final MovimientoHerramientaEquipoService service;
    private final VistasIteracion2 vistas;
    public HerramientaOperacionController(MovimientoHerramientaEquipoService s,VistasIteracion2 v){service=s;vistas=v;}
    @PostMapping("/prestamos")
    public Object prestamo(@Valid @RequestBody Iteracion2Forms.MovimientoHerramienta f){
        if(!"PRESTAMO".equals(f.tipoMovimiento()))throw NegocioException.invalido("Se requiere tipo PRESTAMO");
        return vistas.movimientoHerramienta(service.registrar(f));
    }
    @PostMapping("/devoluciones")
    public Object devolucion(@Valid @RequestBody Iteracion2Forms.MovimientoHerramienta f){
        if(!"DEVOLUCION".equals(f.tipoMovimiento()))throw NegocioException.invalido("Se requiere tipo DEVOLUCION");
        return vistas.movimientoHerramienta(service.registrar(f));
    }
}
