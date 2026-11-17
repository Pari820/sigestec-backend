package pe.edu.sigestec.storage;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface ArchivoStorageService {
    String guardar(MultipartFile archivo, boolean obligatorio, boolean soloPdf);

    Resource cargar(String url);

    String tipoContenido(String url);
}
