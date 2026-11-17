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
@RequestMapping("/api/v1/ejecuciones")
public class EjecucionServicioController {
    private final EjecucionServicioRepository repo;
    private final EjecucionServicioService service;
    private final VistasIteracion2 vistas;
    public EjecucionServicioController(EjecucionServicioRepository r,EjecucionServicioService s,VistasIteracion2 v){repo=r;service=s;vistas=v;}
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERVISOR','SSOMA','ADMIN')")
    public List<?> listar(){return repo.findAll().stream().map(vistas::ejecucion).toList();}
    @PostMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public Object registrar(@Valid @RequestPart("datos") Iteracion2Forms.Ejecucion datos,
        @RequestPart(value="antes",required=false) MultipartFile antes,
        @RequestPart(value="durante",required=false) MultipartFile durante,
        @RequestPart(value="despues",required=false) MultipartFile despues){
        return vistas.ejecucion(service.registrar(datos,antes,durante,despues));
    }
}
