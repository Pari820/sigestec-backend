package pe.edu.sigestec.service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.entity.ConformidadServicio;
public interface ConformidadServicioService {
    ConformidadServicio registrar(Iteracion2Forms.Conformidad datos, MultipartFile acta);
}
