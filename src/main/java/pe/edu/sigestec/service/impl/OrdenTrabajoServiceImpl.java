package pe.edu.sigestec.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.service.*;
import pe.edu.sigestec.storage.ArchivoStorageService;
import pe.edu.sigestec.exception.NegocioException;

import java.time.*;
import java.util.*;

@Service
@Transactional
public class OrdenTrabajoServiceImpl implements OrdenTrabajoService {
    private final OrdenTrabajoRepository ordenes;
    private final AsignacionPersonalRepository asignaciones;
    private final DocumentoPlanificacionRepository documentos;
    private final AnalisisTrabajoSeguroRepository analisis;
    private final ReferenciasService ref;
    private final UsuarioActualService actual;
    private final ArchivoStorageService storage;

    public OrdenTrabajoServiceImpl(OrdenTrabajoRepository ot, AsignacionPersonalRepository a, DocumentoPlanificacionRepository d,
                                   AnalisisTrabajoSeguroRepository ats, ReferenciasService r, UsuarioActualService u, ArchivoStorageService s) {
        ordenes = ot;
        asignaciones = a;
        documentos = d;
        analisis = ats;
        ref = r;
        actual = u;
        storage = s;
    }

    private void disponibilidad(OrdenTrabajo ot, LocalDateTime inicio, LocalDateTime fin) {
        Set<Long> ids = new HashSet<>();
        for (var a : asignaciones.findByOrdenTrabajoIdAndEstado(ot.getId(), "A")) ids.add(a.getEmpleado().getId());
        if (ids.isEmpty()) throw NegocioException.conflicto("La OT necesita personal asignado");
        for (var otra : ordenes.findAll()) {
            if (Objects.equals(otra.getId(), ot.getId()) || otra.getFechaProgramadaInicio() == null || otra.getFechaProgramadaFin() == null
                    || !inicio.isBefore(otra.getFechaProgramadaFin()) || !fin.isAfter(otra.getFechaProgramadaInicio()))
                continue;
            for (var asignacion : asignaciones.findByOrdenTrabajoIdAndEstado(otra.getId(), "A"))
                if (ids.contains(asignacion.getEmpleado().getId()))
                    throw NegocioException.conflicto("Existe cruce de horario del personal con la OT " + otra.getNumeroOrdenTrabajo());
        }
    }

    private void subir(OrdenTrabajo ot, String tipo, MultipartFile archivo, boolean obligatorio, boolean pdf,
                       LocalDate fechaEnvio, String observaciones) {
        var existente = documentos.findByOrdenTrabajoIdAndTipoDocumento(ot.getId(), tipo);
        if (archivo == null || archivo.isEmpty()) {
            if (obligatorio && existente.isEmpty()) throw NegocioException.invalido("Debe adjuntar " + tipo);
            if (existente.isPresent()) {
                DocumentoPlanificacion d = existente.get();
                d.setFechaEnvioCliente(fechaEnvio.atStartOfDay());
                d.setEstado("APROBADO");
                d.setObservaciones(observaciones);
                documentos.save(d);
            }
            return;
        }
        String url = storage.guardar(archivo, true, pdf);
        DocumentoPlanificacion d = existente.orElseGet(DocumentoPlanificacion::new);
        d.setOrdenTrabajo(ot);
        d.setEmpresaCliente(ot.getEmpresaCliente());
        d.setEmpleadoRegistra(actual.empleado());
        d.setTipoDocumento(tipo);
        d.setNombreDocumento(archivo.getOriginalFilename() == null ? tipo : archivo.getOriginalFilename());
        d.setUrlArchivo(url);
        d.setFechaEnvioCliente(fechaEnvio.atStartOfDay());
        // El checkbox registra la declaración de aprobación recibida, NO una firma digital validada.
        d.setEstado("APROBADO");
        d.setObservaciones(observaciones);
        documentos.save(d);
    }

    @Override
    public OrdenTrabajo programar(Long id, LocalDateTime inicio, LocalDateTime fin, String lugar,
                                  LocalDate fechaEnvio, boolean aprobado, MultipartFile pt, MultipartFile iper, MultipartFile croquis, String motivo) {
        OrdenTrabajo ot = ref.orden(id);
        if (!Set.of("ASIGNADA", "PROGRAMADA").contains(ot.getEstado()) || analisis.existsByOrdenTrabajoId(id))
            throw NegocioException.conflicto("OT no disponible para programar o reprogramar");
        if (inicio == null || fin == null || !fin.isAfter(inicio) || !inicio.isAfter(LocalDateTime.now()))
            throw NegocioException.invalido("El horario debe ser futuro y el inicio anterior al fin");
        if (lugar == null || lugar.isBlank() || fechaEnvio == null || !aprobado)
            throw NegocioException.invalido("Lugar, fecha de envío y aprobación del cliente son obligatorios");
        if (fechaEnvio.isAfter(inicio.toLocalDate()))
            throw NegocioException.invalido("La fecha de envío no puede superar la fecha del servicio");
        if (ot.getFechaProgramadaInicio() != null && (motivo == null || motivo.isBlank()))
            throw NegocioException.invalido("Indique motivo de reprogramación");
        disponibilidad(ot, inicio, fin);
        subir(ot, "PROCEDIMIENTO_TRABAJO", pt, true, true, fechaEnvio, motivo);
        subir(ot, "IPER", iper, true, true, fechaEnvio, motivo);
        subir(ot, "CROQUIS", croquis, false, false, fechaEnvio, motivo);
        ot.setFechaProgramadaInicio(inicio);
        ot.setFechaProgramadaFin(fin);
        ot.setLugarTrabajo(lugar.trim());
        ot.setFechaCoordinacionCliente(fechaEnvio.atStartOfDay());
        ot.setEstado("PROGRAMADA");
        ot.getOrdenCompra().getCotizacion().getSolicitudServicio().setEstado("PROGRAMADA");
        return ordenes.save(ot);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoPlanificacion documento(Long id, String tipo) {
        ref.orden(id);
        String key = switch (tipo.toLowerCase(Locale.ROOT)) {
            case "pt" -> "PROCEDIMIENTO_TRABAJO";
            case "iper" -> "IPER";
            case "croquis" -> "CROQUIS";
            default -> throw NegocioException.noExiste("Tipo de documento no reconocido");
        };
        return documentos.findByOrdenTrabajoIdAndTipoDocumento(id, key)
                .orElseThrow(() -> NegocioException.noExiste("Documento no encontrado"));
    }
}
