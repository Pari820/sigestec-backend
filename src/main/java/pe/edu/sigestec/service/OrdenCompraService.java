package pe.edu.sigestec.service;

import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.dto.Forms;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.time.*;
import java.math.*;

public interface OrdenCompraService {
    OrdenCompra registrar(Long cotizacionId, String numero, LocalDate fecha, BigDecimal monto, MultipartFile pdf, List<Forms.DetalleOrdenCompraForm> detalles);
}
