package pe.edu.sigestec.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.exception.NegocioException;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.service.*;
import pe.edu.sigestec.storage.ArchivoStorageService;
import java.time.LocalDate;

@Service
@Transactional
public class ConformidadServicioServiceImpl implements ConformidadServicioService {
    private final ConformidadServicioRepository conformidades;
    private final InformeTecnicoRepository informes;
    private final RepresentanteEmpresaRepository representantes;
    private final OrdenTrabajoRepository ordenes;
    private final ReferenciasService ref;
    private final UsuarioActualService actual;
    private final ArchivoStorageService archivos;
    public ConformidadServicioServiceImpl(ConformidadServicioRepository c,InformeTecnicoRepository i,
             RepresentanteEmpresaRepository r, OrdenTrabajoRepository o, ReferenciasService re, UsuarioActualService u,ArchivoStorageService f){
        conformidades=c;informes=i;representantes=r;ordenes=o;ref=re;actual=u;archivos=f;
    }
    @Override
    public ConformidadServicio registrar(Iteracion2Forms.Conformidad f,MultipartFile acta){
        OrdenTrabajo ot=ref.orden(f.ordenTrabajoId());
        InformeTecnico i=informes.findByOrdenTrabajoId(ot.getId()).orElseThrow(()->NegocioException.conflicto("Primero se debe registrar el informe técnico"));
        if(!"ENVIADO".equals(i.getEstado()))throw NegocioException.conflicto("El informe debe estar ENVIADO al cliente");
        if(conformidades.existsByOrdenTrabajoId(ot.getId()))throw NegocioException.conflicto("Ya existe conformidad para esta OT");
        if(f.fechaConformidad().isBefore(i.getFechaEnvio())||f.fechaConformidad().isAfter(LocalDate.now()))
            throw NegocioException.invalido("La fecha de conformidad debe ser posterior al envío del informe y no futura");
        if(acta==null||acta.isEmpty())throw NegocioException.invalido("Adjunta el acta firmada (PDF, PNG o JPG)");
        ConformidadServicio c=new ConformidadServicio();c.setOrdenTrabajo(ot);c.setInformeTecnico(i);
        c.setEmpleadoRegistra(actual.empleado());c.setNumeroActa(f.numeroActa().trim());
        c.setNombreRepresentante(f.nombreRepresentante().trim());c.setFechaConformidad(f.fechaConformidad());
        c.setValoracion(f.valoracion());c.setEstadoConformidad(f.estadoConformidad());c.setObservaciones(f.observaciones());
        if(f.representanteEmpresaId()!=null){
            RepresentanteEmpresa rep=representantes.findById(f.representanteEmpresaId()).orElseThrow(()->NegocioException.noExiste("Representante no encontrado"));
            if(!rep.getPersonaJuridica().getId().equals(ot.getEmpresaCliente().getId()))throw NegocioException.invalido("El representante no pertenece a la empresa cliente");
            c.setRepresentanteEmpresa(rep);
        }
        c.setUrlArchivo(archivos.guardar(acta,true,false));conformidades.saveAndFlush(c);
        if("CONFORME".equals(c.getEstadoConformidad())) {ot.setEstado("ATENDIDA");ordenes.save(ot);}
        return c;
    }
}
