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
@RequestMapping("/api/v1/ats")
public class AprobacionAtsController {
    private final AprobacionAtsService service;
    private final AprobacionAtsRepository aprobaciones;
    private final AnalisisTrabajoSeguroRepository ats;
    private final Vistas vistas;
    private final VistasIteracion2 vistas2;
    public AprobacionAtsController(AprobacionAtsService s,AprobacionAtsRepository r,
                                  AnalisisTrabajoSeguroRepository a,Vistas v,VistasIteracion2 v2){
        service=s;aprobaciones=r;ats=a;vistas=v;vistas2=v2;
    }
    @GetMapping("/pendientes")
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public List<?> pendientes(){return ats.findAll().stream().filter(a->"PENDIENTE".equals(a.getEstado())||"ELABORADO".equals(a.getEstado())).map(vistas::analisisTrabajoSeguro).toList();}
    @GetMapping("/aprobaciones")
    @PreAuthorize("hasAnyRole('SSOMA','SUPERVISOR','ADMIN')")
    public List<?> listar(){return aprobaciones.findAll().stream().map(vistas2::aprobacion).toList();}
    @PutMapping(value="/{id}/aprobacion",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public Object aprobar(@PathVariable Long id,@Valid @RequestPart("datos") Iteracion2Forms.Aprobacion datos,
                          @RequestPart(value="acta",required=false) MultipartFile acta){
        return vistas2.aprobacion(service.registrar(id,datos,acta));
    }
}
