package pe.edu.sigestec.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pe.edu.sigestec.dto.Forms;
import pe.edu.sigestec.entity.OrdenCompra;
import pe.edu.sigestec.repository.OrdenCompraRepository;
import pe.edu.sigestec.service.*;
import pe.edu.sigestec.storage.ArchivoStorageService;
import pe.edu.sigestec.exception.NegocioException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.math.*;
import java.time.*;

@RestController
@RequestMapping("/api/v1/ordenes-compra")
public class OrdenCompraController {
    private final OrdenCompraRepository repo;
    private final OrdenCompraService service;
    private final Vistas vistas;
    private final ArchivoStorageService storage;
    private final ObjectMapper mapper;

    public OrdenCompraController(OrdenCompraRepository r, OrdenCompraService s, Vistas v, ArchivoStorageService st, ObjectMapper m) {
        repo = r;
        service = s;
        vistas = v;
        storage = st;
        mapper = m;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','SUPERVISOR','ADMIN')")
    public List<?> listar() {
        return repo.findAll().stream().map(vistas::compra).toList();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public Object registrar(@RequestParam Long cotizacionId, @RequestParam String numero,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEmision,
                            @RequestParam BigDecimal montoTotal, @RequestPart("pdf") MultipartFile pdf,
                            @RequestParam(required = false) String detallesJson) {
        List<Forms.DetalleOrdenCompraForm> lineas = null;
        if (detallesJson != null && !detallesJson.isBlank()) try {
            lineas = mapper.readValue(detallesJson, new TypeReference<List<Forms.DetalleOrdenCompraForm>>() {
            });
        } catch (Exception e) {
            throw NegocioException.invalido("Detalle de OC no es JSON válido");
        }
        return vistas.compra(service.registrar(cotizacionId, numero, fechaEmision, montoTotal, pdf, lineas));
    }

    @GetMapping("/{id}/pdf")
    @PreAuthorize("hasAnyRole('GERENTE','SUPERVISOR','ADMIN')")
    public ResponseEntity<Resource> pdf(@PathVariable Long id) {
        OrdenCompra oc = repo.findById(id).orElseThrow(() -> NegocioException.noExiste("OC no encontrada"));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + oc.getNumeroOrdenCompra() + ".pdf\"")
                .body(storage.cargar(oc.getUrlArchivo()));
    }
}
