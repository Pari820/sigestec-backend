package pe.edu.sigestec.service;

import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.dto.Forms;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.time.*;
import java.math.*;

public interface CotizacionService {
    default Cotizacion registrar(Forms.CotizacionForm f) {
        return registrar(f, null);
    }

    Cotizacion registrar(Forms.CotizacionForm f, MultipartFile pdf);

    default Cotizacion editar(Long id, Forms.CotizacionForm f) {
        return editar(id, f, null);
    }

    Cotizacion editar(Long id, Forms.CotizacionForm f, MultipartFile pdf);

    Cotizacion cambiarEstado(Long id, String estado);
}
