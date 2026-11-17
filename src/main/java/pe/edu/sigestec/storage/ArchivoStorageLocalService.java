package pe.edu.sigestec.storage;

import pe.edu.sigestec.exception.NegocioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.io.*;
import java.util.*;

/**
 * Documentos fuera de la BD. Solo la URL queda persistida en Oracle.
 */
@Service
public class ArchivoStorageLocalService implements ArchivoStorageService {
    private final Path raiz;
    private final String urlBase;

    public ArchivoStorageLocalService(@Value("${app.storage.directory:./archivos-sigestec}") String dir,
                                      @Value("${app.storage.public-base-url:http://localhost:8080}") String base) {
        raiz = Path.of(dir).toAbsolutePath().normalize();
        urlBase = base.replaceAll("/+$", "");
        try {
            Files.createDirectories(raiz);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo crear carpeta de documentos", e);
        }
    }

    @Override
    public String guardar(MultipartFile archivo, boolean obligatorio, boolean soloPdf) {
        if (archivo == null || archivo.isEmpty()) {
            if (obligatorio) throw NegocioException.invalido("Debe adjuntar un documento válido");
            return null;
        }
        if (archivo.getSize() > 8 * 1024 * 1024) throw NegocioException.invalido("Archivo supera 8 MB");
        try {
            String original = Optional.ofNullable(archivo.getOriginalFilename()).orElse("").toLowerCase(Locale.ROOT);
            byte[] data = archivo.getBytes();
            boolean pdf = data.length >= 5 && data[0] == '%' && data[1] == 'P' && data[2] == 'D' && data[3] == 'F' && data[4] == '-';
            boolean png = data.length >= 4 && (data[0] & 255) == 137 && (data[1] & 255) == 80 && (data[2] & 255) == 78 && (data[3] & 255) == 71;
            boolean jpg = data.length >= 3 && (data[0] & 255) == 255 && (data[1] & 255) == 216 && (data[2] & 255) == 255;
            String ext = pdf && original.endsWith(".pdf") ? "pdf" : png && original.endsWith(".png") ? "png" :
                    jpg && (original.endsWith(".jpg") || original.endsWith(".jpeg")) ? "jpg" : null;
            if (ext == null || (soloPdf && !ext.equals("pdf")))
                throw NegocioException.invalido("Archivo inválido: use PDF" + (soloPdf ? "" : "/PNG/JPG"));
            String file = UUID.randomUUID() + "." + ext;
            Files.write(raiz.resolve(file), data, StandardOpenOption.CREATE_NEW);
            return urlBase + "/api/v1/archivos/" + file;
        } catch (IOException e) {
            throw NegocioException.invalido("No se pudo almacenar el documento");
        }
    }

    private Path localizar(String url) {
        if (url == null) throw NegocioException.noExiste("Archivo no registrado");
        String file = url.substring(url.lastIndexOf('/') + 1);
        if (!file.matches("[a-f0-9-]{36}\\.(pdf|png|jpg)")) throw NegocioException.invalido("URL de archivo inválida");
        Path ruta = raiz.resolve(file).normalize();
        if (!ruta.startsWith(raiz) || !Files.isRegularFile(ruta))
            throw NegocioException.noExiste("Archivo no encontrado");
        return ruta;
    }

    @Override
    public Resource cargar(String url) {
        return new FileSystemResource(localizar(url));
    }

    @Override
    public String tipoContenido(String url) {
        String file = localizar(url).getFileName().toString();
        return file.endsWith(".pdf") ? "application/pdf" : file.endsWith(".png") ? "image/png" : "image/jpeg";
    }
}
