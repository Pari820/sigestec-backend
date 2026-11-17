package pe.edu.sigestec.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.repository.*;
import java.util.*;

/** Proyección de API desacoplada de entidades JPA; reutiliza los DTO visuales de CU01–CU06. */
@Component
@Transactional(readOnly=true)
public class VistasIteracion2 {
    private final Vistas vistas;
    private final EvidenciaEjecucionRepository evidenciasEjecucion;
    private final MaterialUtilizadoRepository materialUtilizado;
    private final EvidenciaInformeTecnicoRepository evidenciasInforme;
    public VistasIteracion2(Vistas v,EvidenciaEjecucionRepository ee,MaterialUtilizadoRepository mu,EvidenciaInformeTecnicoRepository ei){
        vistas=v;evidenciasEjecucion=ee;materialUtilizado=mu;evidenciasInforme=ei;
    }
    private static String txt(String x){return x==null?"":x;}
    public Map<String,Object> aprobacion(AprobacionAts x){
        Map<String,Object> m=new LinkedHashMap<>();m.put("id",x.getId());
        m.put("atsId",x.getAnalisisTrabajoSeguro().getId());m.put("ordenTrabajoId",x.getAnalisisTrabajoSeguro().getOrdenTrabajo().getId());
        m.put("supervisorCliente",x.getSupervisorCliente());m.put("ssomaCliente",x.getSsomaCliente());
        m.put("fechaRevision",x.getFechaRevision());m.put("resultado",x.getResultado());
        m.put("observaciones",txt(x.getObservaciones()));m.put("urlActa",x.getUrlActa());
        m.put("empleadoRegistra",vistas.empleado(x.getEmpleadoRegistra()));return m;
    }
    public Map<String,Object> ejecucion(EjecucionServicio x){
        Map<String,Object> m=new LinkedHashMap<>();m.put("id",x.getId());
        m.put("ordenTrabajo",vistas.orden(x.getOrdenTrabajo()));m.put("fechaEjecucion",x.getFechaEjecucion());
        m.put("descripcion",x.getDescripcion());m.put("mediciones",txt(x.getMediciones()));m.put("observaciones",txt(x.getObservaciones()));
        m.put("estado",x.getEstado());m.put("empleadoRegistra",vistas.empleado(x.getEmpleadoRegistra()));
        m.put("evidencias",evidenciasEjecucion.findByEjecucionServicioId(x.getId()).stream().map(v -> Map.of(
                "id",v.getId(),"etapa",v.getEtapa(),"urlArchivo",v.getUrlArchivo(),"nombreArchivo",txt(v.getNombreArchivo()))).toList());
        m.put("materiales",materialUtilizado.findByEjecucionServicioId(x.getId()).stream().map(d -> Map.of(
                "id",d.getId(),"material",material(d.getMaterial()),"cantidadReal",d.getCantidadReal(),"estadoValidacion",d.getEstadoValidacion())).toList());
        return m;
    }
    public Map<String,Object> informe(InformeTecnico x){
        Map<String,Object> m=new LinkedHashMap<>();m.put("id",x.getId());
        m.put("ordenTrabajo",vistas.orden(x.getOrdenTrabajo()));m.put("ejecucionId",x.getEjecucionServicio().getId());
        m.put("numeroInforme",x.getNumeroInforme());m.put("fechaInforme",x.getFechaInforme());m.put("fechaEnvio",x.getFechaEnvio());
        m.put("diagnostico",x.getDiagnostico());m.put("trabajosRealizados",x.getTrabajosRealizados());
        m.put("conclusiones",x.getConclusiones());m.put("recomendaciones",txt(x.getRecomendaciones()));
        m.put("estado",x.getEstado());m.put("urlArchivo",x.getUrlArchivo());
        m.put("empleadoRegistra",vistas.empleado(x.getEmpleadoRegistra()));
        m.put("evidencias",evidenciasInforme.findByInformeTecnicoId(x.getId()).stream().map(v -> Map.of(
                "id",v.getId(),"urlArchivo",v.getUrlArchivo(),"nombreArchivo",txt(v.getNombreArchivo()))).toList());
        return m;
    }
    public Map<String,Object> conformidad(ConformidadServicio x){
        Map<String,Object> m=new LinkedHashMap<>();m.put("id",x.getId());
        m.put("ordenTrabajo",vistas.orden(x.getOrdenTrabajo()));m.put("informeId",x.getInformeTecnico().getId());
        m.put("numeroActa",x.getNumeroActa());m.put("fechaConformidad",x.getFechaConformidad());
        m.put("nombreRepresentante",x.getNombreRepresentante());m.put("valoracion",x.getValoracion());
        m.put("estadoConformidad",x.getEstadoConformidad());m.put("observaciones",txt(x.getObservaciones()));
        m.put("urlArchivo",x.getUrlArchivo());m.put("representanteEmpresaId",x.getRepresentanteEmpresa()==null?null:x.getRepresentanteEmpresa().getId());
        m.put("empleadoRegistra",vistas.empleado(x.getEmpleadoRegistra()));return m;
    }
    public Map<String,Object> material(Material x){
        return Map.of("id",x.getId(),"codigo",txt(x.getCodigo()),"nombre",txt(x.getNombre()),"unidadMedida",txt(x.getUnidadMedida()),
                "stockActual",x.getStockActual(),"stockMinimo",x.getStockMinimo(),"estado",txt(x.getEstado()),
                "bajoMinimo",x.getStockActual().compareTo(x.getStockMinimo())<=0);
    }
    public Map<String,Object> movimientoMaterial(MovimientoMaterialAlmacen x){
        Map<String,Object> m=new LinkedHashMap<>();m.put("id",x.getId());m.put("material",material(x.getMaterial()));
        m.put("ordenTrabajoId",x.getOrdenTrabajo()==null?null:x.getOrdenTrabajo().getId());
        m.put("tipoMovimiento",x.getTipoMovimiento());m.put("cantidad",x.getCantidad());
        m.put("fechaMovimiento",x.getFechaMovimiento());m.put("motivo",txt(x.getMotivo()));m.put("observacion",txt(x.getObservacion()));
        m.put("empleadoRegistra",vistas.empleado(x.getEmpleadoRegistra()));return m;
    }
    public Map<String,Object> herramienta(HerramientaEquipo x){
        return Map.of("id",x.getId(),"codigo",txt(x.getCodigoHerramienta()),"nombre",txt(x.getNombre()),
                "descripcion",txt(x.getDescripcion()),"cantidadTotal",x.getCantidadTotal(),"cantidadDisponible",x.getCantidadDisponible(),"estado",txt(x.getEstado()));
    }
    public Map<String,Object> movimientoHerramienta(MovimientoHerramientaEquipo x){
        Map<String,Object> m=new LinkedHashMap<>();m.put("id",x.getId());m.put("herramienta",herramienta(x.getHerramientaEquipo()));
        m.put("ordenTrabajoId",x.getOrdenTrabajo()==null?null:x.getOrdenTrabajo().getId());
        m.put("empleadoResponsable",x.getEmpleadoResponsable()==null?null:vistas.empleado(x.getEmpleadoResponsable()));
        m.put("tipoMovimiento",x.getTipoMovimiento());m.put("cantidad",x.getCantidad());
        m.put("fechaMovimiento",x.getFechaMovimiento());m.put("estadoDevolucion",txt(x.getEstadoDevolucion()));
        m.put("observacion",txt(x.getObservacion()));m.put("empleadoRegistra",vistas.empleado(x.getEmpleadoRegistra()));return m;
    }
}
