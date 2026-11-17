package pe.edu.sigestec.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.exception.NegocioException;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.service.*;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class MovimientoHerramientaEquipoServiceImpl implements MovimientoHerramientaEquipoService {
    private final HerramientaEquipoRepository herramientas;
    private final MovimientoHerramientaEquipoRepository movimientos;
    private final ReferenciasService ref;
    private final UsuarioActualService actual;
    public MovimientoHerramientaEquipoServiceImpl(HerramientaEquipoRepository h, MovimientoHerramientaEquipoRepository m,
                                                  ReferenciasService r, UsuarioActualService u){
        herramientas=h;movimientos=m;ref=r;actual=u;
    }
    @Override
    public HerramientaEquipo crear(Iteracion2Forms.HerramientaCatalogo f){
        if(herramientas.findByNombreIgnoreCase(f.nombre().trim()).isPresent())throw NegocioException.conflicto("Herramienta ya registrada");
        if(herramientas.findByCodigoHerramientaIgnoreCase(f.codigo().trim()).isPresent())throw NegocioException.conflicto("Código de herramienta duplicado");
        HerramientaEquipo h=new HerramientaEquipo();h.setCodigoHerramienta(f.codigo().trim());
        h.setNombre(f.nombre().trim());h.setDescripcion(f.descripcion());
        h.setCantidadTotal(f.cantidadInicial());h.setCantidadDisponible(f.cantidadInicial());h.setEstado("A");
        return herramientas.saveAndFlush(h);
    }
    @Override
    public MovimientoHerramientaEquipo registrar(Iteracion2Forms.MovimientoHerramienta f){
        HerramientaEquipo h=herramientas.buscarParaActualizar(f.herramientaId()).orElseThrow(()->NegocioException.noExiste("Herramienta no encontrada"));
        if(!"A".equals(h.getEstado()))throw NegocioException.conflicto("Herramienta inactiva");
        String t=f.tipoMovimiento();
        OrdenTrabajo ot=null;Empleado responsable=null;
        if(!"INGRESO".equals(t)){
            if(f.ordenTrabajoId()==null||f.empleadoResponsableId()==null)
                throw NegocioException.invalido("Indica la OT y el empleado responsable");
            ot=ref.orden(f.ordenTrabajoId());
            if(!Set.of("PROGRAMADA","EJECUTADA","ATENDIDA").contains(ot.getEstado()))throw NegocioException.conflicto("OT no habilitada");
            responsable=ref.empleado(f.empleadoResponsableId(),"TECNICO");
        }
        BigDecimal cantidad=f.cantidad();
        if("PRESTAMO".equals(t)){
            if(h.getCantidadDisponible().compareTo(cantidad)<0)throw NegocioException.conflicto("No hay herramientas suficientes disponibles");
            h.setCantidadDisponible(h.getCantidadDisponible().subtract(cantidad));
        } else if("DEVOLUCION".equals(t)) {
            BigDecimal saldo=BigDecimal.ZERO;
            for(MovimientoHerramientaEquipo mov:movimientos.findByOrdenTrabajoIdAndHerramientaEquipoIdAndEmpleadoResponsableId(ot.getId(),h.getId(),responsable.getId())){
                if("PRESTAMO".equals(mov.getTipoMovimiento()))saldo=saldo.add(mov.getCantidad());
                if("DEVOLUCION".equals(mov.getTipoMovimiento()))saldo=saldo.subtract(mov.getCantidad());
            }
            if(saldo.compareTo(cantidad)<0)throw NegocioException.conflicto("La devolución excede el préstamo pendiente de este empleado y OT");
            if(f.estadoDevolucion()==null||!Set.of("BUENO","DANADO").contains(f.estadoDevolucion()))
                throw NegocioException.invalido("Especifica el estado BUENO o DANADO de la herramienta devuelta");
            if("BUENO".equals(f.estadoDevolucion()))h.setCantidadDisponible(h.getCantidadDisponible().add(cantidad));
            // Si está dañada se conserva en el inventario total, pero NO vuelve a disponible.
        } else if("INGRESO".equals(t)){
            h.setCantidadTotal(h.getCantidadTotal().add(cantidad));
            h.setCantidadDisponible(h.getCantidadDisponible().add(cantidad));
        } else throw NegocioException.invalido("Movimiento de herramienta no reconocido");
        MovimientoHerramientaEquipo mov=new MovimientoHerramientaEquipo();
        mov.setHerramientaEquipo(h);mov.setOrdenTrabajo(ot);mov.setEmpleadoResponsable(responsable);
        mov.setEmpleadoRegistra(actual.empleado());mov.setTipoMovimiento(t);mov.setCantidad(cantidad);
        mov.setEstadoDevolucion(f.estadoDevolucion());mov.setObservacion(f.observacion());
        herramientas.save(h);return movimientos.saveAndFlush(mov);
    }
}
