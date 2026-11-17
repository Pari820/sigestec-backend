package pe.edu.sigestec.service;

import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.dto.Forms;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.time.*;
import java.math.*;

public interface SolicitudServicioService {
    default SolicitudServicio registrar(Forms.SolicitudForm f) {
        return registrar(f, null);
    }

    SolicitudServicio registrar(Forms.SolicitudForm f, MultipartFile pdf);
}
