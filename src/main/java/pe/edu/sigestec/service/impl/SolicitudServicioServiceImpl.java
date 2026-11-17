package pe.edu.sigestec.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.sigestec.storage.ArchivoStorageService;
import pe.edu.sigestec.dto.Forms;
import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.service.*;
import pe.edu.sigestec.exception.NegocioException;

@Service
@Transactional
public class SolicitudServicioServiceImpl implements SolicitudServicioService {
    private final SolicitudServicioRepository solicitudes;
    private final PersonaJuridicaRepository empresas;
    private final TipoServicioRepository tipos;
    private final RepresentanteEmpresaRepository representantes;
    private final UsuarioActualService actual;
    private final ArchivoStorageService storage;

    public SolicitudServicioServiceImpl(SolicitudServicioRepository s, PersonaJuridicaRepository e,
                                        TipoServicioRepository t, RepresentanteEmpresaRepository r, UsuarioActualService a, ArchivoStorageService storage) {
        solicitudes = s;
        empresas = e;
        tipos = t;
        representantes = r;
        actual = a;
        this.storage = storage;
    }

    @Override
    public SolicitudServicio registrar(Forms.SolicitudForm f, MultipartFile pdf) {
        PersonaJuridica pj = empresas.findById(f.clienteId()).orElseThrow(() -> NegocioException.noExiste("Empresa no encontrada"));
        TipoServicio ts = tipos.findById(f.tipoServicioId()).orElseThrow(() -> NegocioException.noExiste("Tipo de servicio no encontrado"));
        SolicitudServicio s = new SolicitudServicio();
        s.setEmpresaCliente(pj);
        s.setTipoServicio(ts);
        if (f.representanteId() != null) {
            RepresentanteEmpresa r = representantes.findById(f.representanteId()).orElseThrow(() -> NegocioException.noExiste("Representante no encontrado"));
            if (!pj.getId().equals(r.getPersonaJuridica().getId()))
                throw NegocioException.invalido("El representante no pertenece a la empresa cliente");
            s.setRepresentanteSolicitante(r);
        }
        s.setDescripcion(f.descripcion().trim());
        s.setObservaciones(f.observaciones());
        s.setLugarServicio(f.lugarServicio());
        s.setEmpleadoRegistra(actual.empleado());
        s.setEstado("PENDIENTE");
        s.setUrlArchivoAdjunto(storage.guardar(pdf, false, true));
        return solicitudes.save(s);
    }
}
