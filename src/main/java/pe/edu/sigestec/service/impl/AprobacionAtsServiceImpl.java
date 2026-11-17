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

@Service
@Transactional
public class AprobacionAtsServiceImpl implements AprobacionAtsService {
    private final AnalisisTrabajoSeguroRepository analisis;
    private final AprobacionAtsRepository aprobaciones;
    private final UsuarioActualService actual;
    private final ArchivoStorageService archivos;
    public AprobacionAtsServiceImpl(AnalisisTrabajoSeguroRepository a, AprobacionAtsRepository r, UsuarioActualService u, ArchivoStorageService f) {
        analisis=a; aprobaciones=r; actual=u; archivos=f;
    }
    @Override
    public AprobacionAts registrar(Long idAts, Iteracion2Forms.Aprobacion f, MultipartFile acta) {
        AnalisisTrabajoSeguro ats=analisis.findById(idAts).orElseThrow(()->NegocioException.noExiste("ATS no encontrado"));
        if (!"PENDIENTE".equals(ats.getEstado()) && !"ELABORADO".equals(ats.getEstado()))
            throw NegocioException.conflicto("Solo se pueden resolver ATS pendientes de aprobación");
        if (aprobaciones.existsByAnalisisTrabajoSeguroId(idAts)) throw NegocioException.conflicto("ATS ya resuelto");
        if (f.fechaRevision().isAfter(java.time.LocalDate.now()) || f.fechaRevision().isBefore(ats.getFechaRegistro()))
            throw NegocioException.invalido("La fecha de revisión no es válida");
        if ("APROBADO".equals(f.resultado()) && (acta==null || acta.isEmpty()))
            throw NegocioException.invalido("Adjunta el acta con las firmas o aprobación del cliente");
        if ("RECHAZADO".equals(f.resultado()) && (f.observaciones()==null || f.observaciones().isBlank()))
            throw NegocioException.invalido("Indica el motivo de rechazo del ATS");
        AprobacionAts aprobacion=new AprobacionAts();
        aprobacion.setAnalisisTrabajoSeguro(ats);
        aprobacion.setEmpleadoRegistra(actual.empleado());
        aprobacion.setFechaRevision(f.fechaRevision());
        aprobacion.setSupervisorCliente(f.supervisorCliente().trim());
        aprobacion.setSsomaCliente(f.ssomaCliente().trim());
        aprobacion.setResultado(f.resultado());
        aprobacion.setObservaciones(f.observaciones());
        aprobacion.setUrlActa(archivos.guardar(acta,false,false));
        aprobaciones.saveAndFlush(aprobacion);
        ats.setEstado(f.resultado());
        analisis.save(ats);
        return aprobacion;
    }
}
