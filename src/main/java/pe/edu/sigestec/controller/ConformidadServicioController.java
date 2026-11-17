package pe.edu.sigestec.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.sigestec.dto.Iteracion2Forms;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.service.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/conformidades")
public class ConformidadServicioController {
    private final ConformidadServicioRepository repo;
    private final ConformidadServicioService service;
    private final VistasIteracion2 vistas;
    public ConformidadServicioController(ConformidadServicioRepository r,ConformidadServicioService s,VistasIteracion2 v){repo=r;service=s;vistas=v;}
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public List<?> listar(){return repo.findAll().stream().map(vistas::conformidad).toList();}
    @PostMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public Object registrar(@Valid @RequestPart("datos") Iteracion2Forms.Conformidad datos,
                            @RequestPart("acta") MultipartFile acta){
        return vistas.conformidad(service.registrar(datos,acta));
    }
}
