package pe.edu.sigestec.service;

import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.dto.Forms;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.time.*;
import java.math.*;

public interface AnalisisTrabajoSeguroService {
    default AnalisisTrabajoSeguro elaborar(Forms.AtsForm f) {
        return elaborar(f, null);
    }

    AnalisisTrabajoSeguro elaborar(Forms.AtsForm f, MultipartFile pdf);
}
