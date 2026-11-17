package pe.edu.sigestec.service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.entity.AprobacionAts;
public interface AprobacionAtsService {
    AprobacionAts registrar(Long idAts, Iteracion2Forms.Aprobacion datos, MultipartFile acta);
}
