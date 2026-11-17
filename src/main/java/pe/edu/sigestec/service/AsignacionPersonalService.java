package pe.edu.sigestec.service;

import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.dto.Forms;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.time.*;
import java.math.*;

public interface AsignacionPersonalService {
    OrdenTrabajo registrar(Forms.AsignacionForm f);

    OrdenTrabajo cambiarEquipo(Long id, Forms.CambioEquipoForm f);
}
