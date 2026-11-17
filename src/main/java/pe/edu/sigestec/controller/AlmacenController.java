package pe.edu.sigestec.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.service.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/almacen")
public class AlmacenController {
    private final MaterialRepository materiales;
    private final MovimientoMaterialAlmacenRepository movimientosMaterial;
    private final HerramientaEquipoRepository herramientas;
    private final MovimientoHerramientaEquipoRepository movimientosHerramienta;
    private final MovimientoMaterialAlmacenService servicioMaterial;
    private final MovimientoHerramientaEquipoService servicioHerramienta;
    private final VistasIteracion2 vistas;
    public AlmacenController(MaterialRepository m,MovimientoMaterialAlmacenRepository mm,HerramientaEquipoRepository h,
        MovimientoHerramientaEquipoRepository mh, MovimientoMaterialAlmacenService sm,
        MovimientoHerramientaEquipoService sh,VistasIteracion2 v){
        materiales=m;movimientosMaterial=mm;herramientas=h;movimientosHerramienta=mh;
        servicioMaterial=sm;servicioHerramienta=sh;vistas=v;
    }
    @GetMapping("/materiales") @PreAuthorize("hasAnyRole('SUPERVISOR','ALMACENERO','ADMIN')") public List<?> materiales(){return materiales.findAll().stream().map(vistas::material).toList();}
    @PostMapping("/materiales") @PreAuthorize("hasAnyRole('ALMACENERO','ADMIN')") public Object material(@Valid @RequestBody Iteracion2Forms.MaterialCatalogo f){return vistas.material(servicioMaterial.crear(f));}
    @GetMapping("/movimientos") @PreAuthorize("hasAnyRole('ALMACENERO','ADMIN')") public List<?> movimientos(){return movimientosMaterial.findAll().stream().map(vistas::movimientoMaterial).toList();}
    @PostMapping("/movimientos") @PreAuthorize("hasAnyRole('ALMACENERO','ADMIN')") public Object movimiento(@Valid @RequestBody Iteracion2Forms.MovimientoMaterial f){return vistas.movimientoMaterial(servicioMaterial.registrar(f));}
    /** Alias del mensaje HTTP del diagrama CU11. */
    @PostMapping("/devoluciones") @PreAuthorize("hasAnyRole('ALMACENERO','ADMIN')")
    public Object devolucion(@Valid @RequestBody Iteracion2Forms.MovimientoMaterial f){
        if(!"DEVOLUCION".equals(f.tipoMovimiento()))throw pe.edu.sigestec.exception.NegocioException.invalido("Se requiere tipo DEVOLUCION");
        return vistas.movimientoMaterial(servicioMaterial.registrar(f));
    }
    @GetMapping("/herramientas") @PreAuthorize("hasAnyRole('ALMACENERO','ADMIN')") public List<?> herramientas(){return herramientas.findAll().stream().map(vistas::herramienta).toList();}
    @PostMapping("/herramientas") @PreAuthorize("hasAnyRole('ALMACENERO','ADMIN')") public Object herramienta(@Valid @RequestBody Iteracion2Forms.HerramientaCatalogo f){return vistas.herramienta(servicioHerramienta.crear(f));}
    @GetMapping("/herramientas/movimientos") @PreAuthorize("hasAnyRole('ALMACENERO','ADMIN')") public List<?> movimientosHerramienta(){return movimientosHerramienta.findAll().stream().map(vistas::movimientoHerramienta).toList();}
    @PostMapping("/herramientas/movimientos") @PreAuthorize("hasAnyRole('ALMACENERO','ADMIN')") public Object movimientoHerramienta(@Valid @RequestBody Iteracion2Forms.MovimientoHerramienta f){return vistas.movimientoHerramienta(servicioHerramienta.registrar(f));}
}
