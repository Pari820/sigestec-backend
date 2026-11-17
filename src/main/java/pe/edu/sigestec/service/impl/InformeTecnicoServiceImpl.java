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
import java.util.*;

@Service
@Transactional
public class InformeTecnicoServiceImpl implements InformeTecnicoService {
    private final InformeTecnicoRepository informes;
    private final EjecucionServicioRepository ejecuciones;
    private final EvidenciaInformeTecnicoRepository evidencias;
    private final ReferenciasService ref;
    private final UsuarioActualService actual;
    private final ArchivoStorageService archivos;
    public InformeTecnicoServiceImpl(InformeTecnicoRepository i, EjecucionServicioRepository e,
              EvidenciaInformeTecnicoRepository v, ReferenciasService r, UsuarioActualService u, ArchivoStorageService f){
        informes=i;ejecuciones=e;evidencias=v;ref=r;actual=u;archivos=f;
    }
    @Override
    public InformeTecnico registrar(Iteracion2Forms.Informe f, MultipartFile pdf, List<MultipartFile> fotos){
        OrdenTrabajo ot=ref.orden(f.ordenTrabajoId());
        if(!"EJECUTADA".equals(ot.getEstado()))throw NegocioException.conflicto("La OT debe tener una ejecución registrada");
        EjecucionServicio e=ejecuciones.findByOrdenTrabajoId(ot.getId()).orElseThrow(()->NegocioException.conflicto("No existe ejecución"));
        if(informes.existsByOrdenTrabajoId(ot.getId()))throw NegocioException.conflicto("La OT ya tiene un informe técnico");
        if(f.fechaEnvio().isBefore(e.getFechaEjecucion().toLocalDate()) || f.fechaEnvio().isAfter(LocalDate.now()))
            throw NegocioException.invalido("La fecha de envío no puede ser anterior a la ejecución ni futura");
        InformeTecnico i=new InformeTecnico();
        i.setOrdenTrabajo(ot);i.setEjecucionServicio(e);i.setEmpleadoRegistra(actual.empleado());
        i.setDiagnostico(f.diagnostico().trim());i.setTrabajosRealizados(f.trabajosRealizados().trim());
        i.setConclusiones(f.conclusiones().trim());i.setRecomendaciones(f.recomendaciones());
        i.setFechaEnvio(f.fechaEnvio());i.setEstado("ENVIADO");
        i.setUrlArchivo(archivos.guardar(pdf,false,true));
        informes.saveAndFlush(i);
        i.setNumeroInforme(f.numeroInforme()==null || f.numeroInforme().isBlank()?"INF-"+String.format("%05d",i.getId()):f.numeroInforme().trim());
        informes.save(i);
        if(fotos!=null)for(MultipartFile foto:fotos){
            if(foto==null||foto.isEmpty())continue;
            String tipo=Objects.toString(foto.getContentType(),"");
            if(!tipo.equals("image/png") && !tipo.equals("image/jpeg"))throw NegocioException.invalido("La evidencia debe ser PNG o JPG");
            EvidenciaInformeTecnico v=new EvidenciaInformeTecnico();v.setInformeTecnico(i);
            v.setUrlArchivo(archivos.guardar(foto,true,false));v.setNombreArchivo(foto.getOriginalFilename());evidencias.save(v);
        }
        return i;
    }
}
