package pe.edu.sigestec.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.exception.NegocioException;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.service.*;
import java.math.BigDecimal;

@Service
@Transactional
public class MovimientoMaterialAlmacenServiceImpl implements MovimientoMaterialAlmacenService {
    private final MaterialRepository materiales;
    private final MovimientoMaterialAlmacenRepository movimientos;
    private final MaterialUtilizadoRepository materialesUsados;
    private final ReferenciasService ref;
    private final UsuarioActualService actual;
    public MovimientoMaterialAlmacenServiceImpl(MaterialRepository m, MovimientoMaterialAlmacenRepository mo, MaterialUtilizadoRepository mu, ReferenciasService r, UsuarioActualService u) {
        materiales=m;movimientos=mo;materialesUsados=mu;ref=r;actual=u;
    }
    @Override
    public Material crear(Iteracion2Forms.MaterialCatalogo f){
        if(materiales.findByCodigoIgnoreCase(f.codigo().trim()).isPresent())throw NegocioException.conflicto("Código de material duplicado");
        Material m=new Material();m.setCodigo(f.codigo().trim());m.setNombre(f.nombre().trim());
        m.setUnidadMedida(f.unidadMedida().trim());m.setStockActual(f.stockInicial());m.setStockMinimo(f.stockMinimo());
        m.setEstado("A");return materiales.saveAndFlush(m);
    }
    @Override
    public MovimientoMaterialAlmacen registrar(Iteracion2Forms.MovimientoMaterial f){
        Material m=materiales.buscarParaActualizar(f.materialId()).orElseThrow(()->NegocioException.noExiste("Material no encontrado"));
        if(!"A".equals(m.getEstado()))throw NegocioException.conflicto("Material inactivo");
        String t=f.tipoMovimiento();
        OrdenTrabajo ot=null;
        if(!"ENTRADA".equals(t)){
            if(f.ordenTrabajoId()==null)throw NegocioException.invalido("Indica la OT para salidas y devoluciones");
            ot=ref.orden(f.ordenTrabajoId());
            if(!java.util.Set.of("PROGRAMADA","EJECUTADA","ATENDIDA").contains(ot.getEstado()))
                throw NegocioException.conflicto("La OT no se encuentra habilitada para movimientos");
        }
        BigDecimal cantidad=f.cantidad();
        if("SALIDA".equals(t)){
            if(m.getStockActual().compareTo(cantidad)<0)throw NegocioException.conflicto("Stock insuficiente para la salida");
            m.setStockActual(m.getStockActual().subtract(cantidad));
        } else if("DEVOLUCION".equals(t)) {
            BigDecimal saldo=BigDecimal.ZERO;
            for(MovimientoMaterialAlmacen mov:movimientos.findByOrdenTrabajoIdAndMaterialId(ot.getId(),m.getId())){
                if("SALIDA".equals(mov.getTipoMovimiento()))saldo=saldo.add(mov.getCantidad());
                if("DEVOLUCION".equals(mov.getTipoMovimiento()))saldo=saldo.subtract(mov.getCantidad());
            }
            if(saldo.compareTo(cantidad)<0)throw NegocioException.conflicto("La devolución supera la cantidad entregada a esta OT");
            m.setStockActual(m.getStockActual().add(cantidad));
        } else if("ENTRADA".equals(t)) {
            m.setStockActual(m.getStockActual().add(cantidad));
        } else throw NegocioException.invalido("Tipo de movimiento no soportado");
        MovimientoMaterialAlmacen mov=new MovimientoMaterialAlmacen();
        mov.setMaterial(m);mov.setOrdenTrabajo(ot);mov.setEmpleadoRegistra(actual.empleado());
        mov.setTipoMovimiento(t);mov.setCantidad(cantidad);mov.setMotivo(f.motivo());mov.setObservacion(f.observacion());
        materiales.save(m);
        movimientos.saveAndFlush(mov);
        if(ot!=null){
            var declarados=materialesUsados.findByOtYMaterial(ot.getId(),m.getId());
            if(!declarados.isEmpty()){
                BigDecimal declarado=declarados.stream().map(MaterialUtilizado::getCantidadReal).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal salidaNeta=BigDecimal.ZERO;
                for(MovimientoMaterialAlmacen movimiento:movimientos.findByOrdenTrabajoIdAndMaterialId(ot.getId(),m.getId())){
                    if("SALIDA".equals(movimiento.getTipoMovimiento()))salidaNeta=salidaNeta.add(movimiento.getCantidad());
                    if("DEVOLUCION".equals(movimiento.getTipoMovimiento()))salidaNeta=salidaNeta.subtract(movimiento.getCantidad());
                }
                String estado=salidaNeta.compareTo(declarado)>=0?"VALIDADO":"PENDIENTE";
                declarados.forEach(d->{d.setEstadoValidacion(estado);materialesUsados.save(d);});
            }
        }
        return mov;
    }
}
