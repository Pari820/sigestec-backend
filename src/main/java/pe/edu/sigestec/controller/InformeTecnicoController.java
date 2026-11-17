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
@RequestMapping("/api/v1/informes")
public class InformeTecnicoController {
    private final InformeTecnicoRepository repo;
    private final InformeTecnicoService service;
    private final VistasIteracion2 vistas;
    public InformeTecnicoController(InformeTecnicoRepository r,InformeTecnicoService s,VistasIteracion2 v){repo=r;service=s;vistas=v;}
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public List<?> listar(){return repo.findAll().stream().map(vistas::informe).toList();}
    @PostMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public Object registrar(@Valid @RequestPart("datos") Iteracion2Forms.Informe datos,
       @RequestPart(value="pdf",required=false) MultipartFile pdf,
       @RequestPart(value="fotos",required=false) List<MultipartFile> fotos){
       return vistas.informe(service.registrar(datos,pdf,fotos));
    }
}
