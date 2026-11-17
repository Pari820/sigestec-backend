package pe.edu.sigestec.controller;

import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.dto.Forms;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.service.Vistas;
import pe.edu.sigestec.exception.NegocioException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
@Transactional
public class CatalogosController {
    private final PerfilRepository perfiles;
    private final PersonaNaturalRepository naturales;
    private final PersonaJuridicaRepository empresas;
    private final RepresentanteEmpresaRepository representantes;
    private final TipoServicioRepository tipos;
    private final EmpleadoRepository empleados;
    private final CargoRepository cargos;
    private final TecnicoRepository tecnicos;
    private final Vistas vistas;

    public CatalogosController(PerfilRepository p, PersonaNaturalRepository pn, PersonaJuridicaRepository pj,
                               RepresentanteEmpresaRepository r, TipoServicioRepository t, EmpleadoRepository e, CargoRepository c, TecnicoRepository te, Vistas v) {
        perfiles = p;
        naturales = pn;
        empresas = pj;
        representantes = r;
        tipos = t;
        empleados = e;
        cargos = c;
        tecnicos = te;
        vistas = v;
    }

    @GetMapping("/clientes")
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public List<?> clientes() {
        return empresas.findAll().stream().map(vistas::cliente).toList();
    }

    @PostMapping("/clientes")
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public Object cliente(@Valid @RequestBody Forms.ClienteForm f) {
        if (empresas.findByRuc(f.ruc()).isPresent()) throw NegocioException.conflicto("RUC ya registrado");
        Perfil p = new Perfil();
        p.setTipoPerfil("JURIDICA");
        perfiles.save(p);
        PersonaJuridica pj = new PersonaJuridica();
        pj.setPerfil(p);
        pj.setRuc(f.ruc());
        pj.setRazonSocial(f.razonSocial().trim());
        pj.setTelefono(f.telefono());
        empresas.save(pj);
        if (f.contacto() != null && !f.contacto().isBlank()) {
            Perfil perfil = new Perfil();
            perfil.setTipoPerfil("NATURAL");
            perfiles.save(perfil);
            PersonaNatural pn = new PersonaNatural();
            pn.setPerfil(perfil);
            String nombre = f.contacto().trim();
            int i = nombre.lastIndexOf(' ');
            pn.setNombres(i < 0 ? nombre : nombre.substring(0, i));
            pn.setApellidos(i < 0 ? "No consignado" : nombre.substring(i + 1));
            naturales.save(pn);
            RepresentanteEmpresa rep = new RepresentanteEmpresa();
            rep.setPersonaNatural(pn);
            rep.setPersonaJuridica(pj);
            rep.setTipoRepresentante("CONTACTO");
            representantes.save(rep);
        }
        return vistas.cliente(pj);
    }

    @GetMapping("/tipos-servicio")
    @PreAuthorize("hasAnyRole('GERENTE','SUPERVISOR','SSOMA','ADMIN')")
    public List<?> tipos() {
        return tipos.findAll().stream().map(vistas::tipo).toList();
    }

    @PostMapping("/tipos-servicio")
    @PreAuthorize("hasAnyRole('GERENTE','ADMIN')")
    public Object tipo(@Valid @RequestBody Forms.TipoForm f) {
        if (tipos.findByNombreIgnoreCase(f.nombre()).isPresent())
            throw NegocioException.conflicto("Servicio duplicado");
        TipoServicio t = new TipoServicio();
        t.setNombre(f.nombre().trim());
        t.setDescripcion(f.descripcion());
        return vistas.tipo(tipos.save(t));
    }

    @GetMapping("/personal")
    @PreAuthorize("hasAnyRole('SUPERVISOR','SSOMA','ALMACENERO','ADMIN')")
    public List<?> personal() {
        return empleados.findAll().stream().map(vistas::empleado).toList();
    }

    @PostMapping("/personal")
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    public Object personal(@Valid @RequestBody Forms.PersonalForm f) {
        if (!Set.of("TECNICO", "SSOMA", "APOYO").contains(f.perfil()))
            throw NegocioException.invalido("Perfil no válido");
        Cargo cargo = cargos.findByNombreIgnoreCase(f.perfil()).orElseGet(() -> {
            Cargo c = new Cargo();
            c.setNombre(f.perfil());
            return cargos.save(c);
        });
        Perfil p = new Perfil();
        p.setTipoPerfil("NATURAL");
        perfiles.save(p);
        PersonaNatural pn = new PersonaNatural();
        pn.setPerfil(p);
        String s = f.nombre().trim();
        int i = s.lastIndexOf(' ');
        pn.setNombres(i < 0 ? s : s.substring(0, i));
        pn.setApellidos(i < 0 ? "No consignado" : s.substring(i + 1));
        naturales.save(pn);
        Empleado e = new Empleado();
        e.setPersonaNatural(pn);
        e.setCargo(cargo);
        e.setCodigoEmpleado("EMP-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase());
        empleados.saveAndFlush(e);
        if ("TECNICO".equals(f.perfil())) {
            Tecnico te = new Tecnico();
            te.setEmpleado(e);
            tecnicos.save(te);
        }
        return vistas.empleado(e);
    }
}
