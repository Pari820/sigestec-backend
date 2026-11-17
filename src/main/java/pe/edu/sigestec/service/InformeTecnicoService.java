package pe.edu.sigestec.service;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.entity.InformeTecnico;
public interface InformeTecnicoService {
    InformeTecnico registrar(Iteracion2Forms.Informe datos, MultipartFile pdf, List<MultipartFile> fotos);
}
