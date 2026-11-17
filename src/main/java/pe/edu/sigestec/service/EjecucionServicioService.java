package pe.edu.sigestec.service;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.entity.EjecucionServicio;
public interface EjecucionServicioService {
    EjecucionServicio registrar(Iteracion2Forms.Ejecucion datos, MultipartFile antes, MultipartFile durante, MultipartFile despues);
}
