package pe.edu.sigestec.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.sigestec.dto.Forms;
import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.service.*;
import pe.edu.sigestec.storage.ArchivoStorageService;
import pe.edu.sigestec.exception.NegocioException;

import java.math.*;
import java.time.*;
import java.util.*;

@Service
@Transactional
public class OrdenCompraServiceImpl implements OrdenCompraService {
    private final OrdenCompraRepository compras;
    private final DetalleOrdenCompraRepository detalles;
    private final DetalleCotizacionRepository detalleCotizacion;
    private final ReferenciasService ref;
    private final UsuarioActualService actual;
    private final ArchivoStorageService storage;

    public OrdenCompraServiceImpl(OrdenCompraRepository c, DetalleOrdenCompraRepository d,
                                  DetalleCotizacionRepository dc, ReferenciasService r, UsuarioActualService a, ArchivoStorageService s) {
        compras = c;
        detalles = d;
        detalleCotizacion = dc;
        ref = r;
        actual = a;
        storage = s;
    }

    @Override
    public OrdenCompra registrar(Long cotizacionId, String numero, LocalDate fecha, BigDecimal monto,
                                 MultipartFile pdf, List<Forms.DetalleOrdenCompraForm> lineas) {
        Cotizacion c = ref.cotizacion(cotizacionId);
        if (!"ACEPTADA".equals(c.getEstado())) throw NegocioException.conflicto("La cotización debe estar ACEPTADA");
        if (compras.existsByCotizacionId(cotizacionId)) throw NegocioException.conflicto("La cotización ya tiene OC");
        if (numero == null || numero.isBlank() || compras.existsByNumeroOrdenCompraIgnoreCase(numero.trim()))
            throw NegocioException.conflicto("Número de OC vacío o duplicado");
        if (fecha == null || monto == null || monto.signum() < 0)
            throw NegocioException.invalido("Fecha y monto válidos son obligatorios");
        // El documento lo emite el cliente y puede tener formatos distintos.
        String url = storage.guardar(pdf, true, true);
        OrdenCompra oc = new OrdenCompra();
        oc.setCotizacion(c);
        oc.setEmpresaCliente(c.getEmpresaCliente());
        oc.setEmpleadoRegistra(actual.empleado());
        oc.setNumeroOrdenCompra(numero.trim());
        oc.setFechaEmision(fecha);
        oc.setMontoTotal(monto);
        oc.setUrlArchivo(url);
        compras.saveAndFlush(oc);
        if (lineas == null || lineas.isEmpty()) {
            lineas = detalleCotizacion.findByCotizacionIdOrderByItemAsc(cotizacionId).stream()
                    .map(d -> new Forms.DetalleOrdenCompraForm(d.getDescripcion(), d.getCantidad(), d.getPrecioUnitario(), d.getUnidadMedida())).toList();
        }
        if (lineas.isEmpty()) throw NegocioException.invalido("La OC requiere al menos un detalle");
        int i = 1;
        for (var d : lineas) {
            if (d.descripcion() == null || d.descripcion().isBlank() || d.cantidad() == null || d.precioUnitario() == null
                    || d.cantidad().signum() <= 0 || d.precioUnitario().signum() < 0)
                throw NegocioException.invalido("Detalle de OC inválido");
            DetalleOrdenCompra det = new DetalleOrdenCompra();
            det.setOrdenCompra(oc);
            det.setItem(i++);
            det.setDescripcion(d.descripcion());
            det.setCantidad(d.cantidad());
            det.setPrecioUnitario(d.precioUnitario());
            det.setUnidadMedida(d.unidadMedida() == null ? "SERVICIO" : d.unidadMedida());
            det.setSubtotal(d.cantidad().multiply(d.precioUnitario()).setScale(2, RoundingMode.HALF_UP));
            detalles.save(det);
        }
        return oc;
    }
}
