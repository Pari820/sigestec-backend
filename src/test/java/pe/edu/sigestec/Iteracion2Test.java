package pe.edu.sigestec;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mock.web.MockMultipartFile;
import pe.edu.sigestec.dto.*;
import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.exception.NegocioException;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.service.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/** Prueba de integración para CU07–CU12. Ejecutar con Maven en un entorno Spring Boot/H2 real. */
@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:sigestec_cu0712_test;MODE=Oracle;DB_CLOSE_DELAY=-1",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "app.demo.enabled=true",
    "app.storage.directory=./archivos-sigestec-test"
})
@Transactional
class Iteracion2Test {
    @Autowired SolicitudServicioService solicitudes;
    @Autowired CotizacionService cotizaciones;
    @Autowired OrdenCompraService compras;
    @Autowired AsignacionPersonalService asignaciones;
    @Autowired OrdenTrabajoService programacion;
    @Autowired AnalisisTrabajoSeguroService analisis;
    @Autowired AprobacionAtsService aprobaciones;
    @Autowired EjecucionServicioService ejecuciones;
    @Autowired InformeTecnicoService informes;
    @Autowired ConformidadServicioService conformidades;
    @Autowired MovimientoMaterialAlmacenService materialService;
    @Autowired MovimientoHerramientaEquipoService herramientaService;
    @Autowired PersonaJuridicaRepository empresas;
    @Autowired TipoServicioRepository tipos;
    @Autowired EmpleadoRepository empleados;
    @Autowired AnalisisTrabajoSeguroRepository atsRepo;
    @Autowired MaterialUtilizadoRepository declarados;
    @Autowired OrdenTrabajoRepository ordenes;

    private void usuario(String nombre){
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(nombre,null,Collections.emptyList()));
    }
    @AfterEach void limpiar(){SecurityContextHolder.clearContext();}
    private MockMultipartFile pdf(String nombre){
        return new MockMultipartFile("pdf",nombre,"application/pdf","%PDF-1.4\n%%EOF".getBytes());
    }
    private MockMultipartFile foto(String nombre){
        return new MockMultipartFile("antes",nombre,"image/png",new byte[]{(byte)137,80,78,71,13,10,26,10,0,0,0,0});
    }
    private OrdenTrabajo prepararOT(){
        usuario("gerente");
        SolicitudServicio solicitud=solicitudes.registrar(new Forms.SolicitudForm(
            empresas.findAll().get(0).getId(), tipos.findAll().get(0).getId(),
            "Inspección eléctrica", "Observación",null,null));
        Cotizacion cot=cotizaciones.registrar(new Forms.CotizacionForm(solicitud.getId(),"Inspección",
            "Cotización",List.of(new Forms.DetalleCotizacionForm("Revisión",BigDecimal.ONE,new BigDecimal("200.00"),"SERVICIO"))));
        cotizaciones.cambiarEstado(cot.getId(),"ACEPTADA");
        compras.registrar(cot.getId(),"OC-CU12-"+UUID.randomUUID().toString().substring(0,7),LocalDate.now(),cot.getTotal(),pdf("oc.pdf"),null);
        Empleado tecnico=empleados.findAll().stream().filter(e->"TECNICO".equals(e.getCargo().getNombre())).findFirst().orElseThrow();
        Empleado ssoma=empleados.findAll().stream().filter(e->"SSOMA".equals(e.getCargo().getNombre())).findFirst().orElseThrow();
        usuario("supervisor");
        OrdenTrabajo ot=asignaciones.registrar(new Forms.AsignacionForm(solicitud.getId(),tecnico.getId(),ssoma.getId(),null,List.of()));
        LocalDateTime inicio=LocalDateTime.now().plusDays(2).withSecond(0).withNano(0);
        programacion.programar(ot.getId(),inicio,inicio.plusHours(2),"Sala de tableros",LocalDate.now(),true,
            pdf("pt.pdf"),pdf("iper.pdf"),null,null);
        return ot;
    }
    @Test void desdeAprobacionHastaConformidadYCambiosDeAlmacen(){
        OrdenTrabajo ot=prepararOT();
        usuario("ssoma");
        AnalisisTrabajoSeguro ats=analisis.elaborar(new Forms.AtsForm(ot.getId(),"Área señalizada",
            List.of("Riesgo eléctrico"),List.of("Guantes"),"Multímetro",
            List.of(new Forms.AtsEtapa("Inspección","Contacto eléctrico","Electrocución","ALTO","Desenergizar"))));
        usuario("almacenero");
        Material material=materialService.crear(new Iteracion2Forms.MaterialCatalogo(
            "MAT-TEST-01","Cable de prueba","METRO",new BigDecimal("10"),new BigDecimal("2")));
        materialService.registrar(new Iteracion2Forms.MovimientoMaterial(material.getId(),ot.getId(),"SALIDA",
            new BigDecimal("3"),"Instalación","Entrega a cuadrilla"));
        HerramientaEquipo herramienta=herramientaService.crear(new Iteracion2Forms.HerramientaCatalogo(
            "HER-TEST-01","Pinza amperimétrica","Prueba",new BigDecimal("4")));
        Empleado tecnico=empleados.findAll().stream().filter(e->"TECNICO".equals(e.getCargo().getNombre())).findFirst().orElseThrow();
        herramientaService.registrar(new Iteracion2Forms.MovimientoHerramienta(herramienta.getId(),ot.getId(),tecnico.getId(),
            "PRESTAMO",new BigDecimal("2"),null,null));
        assertEquals(0,herramienta.getCantidadDisponible().compareTo(new BigDecimal("2")));
        usuario("supervisor");
        Iteracion2Forms.Ejecucion datos=new Iteracion2Forms.Ejecucion(ot.getId(),"Pruebas de cableado", "220 V","Sin incidentes",
            List.of(new Iteracion2Forms.MaterialReal(material.getId(),new BigDecimal("3"))));
        assertThrows(NegocioException.class,()->ejecuciones.registrar(datos,foto("antes.png"),null,null));
        aprobaciones.registrar(ats.getId(),new Iteracion2Forms.Aprobacion("Supervisor de cliente","SSOMA de cliente",
            LocalDate.now(),"APROBADO","Firmas externas comprobadas"),pdf("acta-ats.pdf"));
        assertEquals("APROBADO",atsRepo.findById(ats.getId()).orElseThrow().getEstado());
        EjecucionServicio eje=ejecuciones.registrar(datos,foto("antes.png"),null,null);
        assertEquals("REGISTRADA",eje.getEstado());
        assertEquals("VALIDADO",declarados.findByEjecucionServicioId(eje.getId()).get(0).getEstadoValidacion());
        InformeTecnico informe=informes.registrar(new Iteracion2Forms.Informe(ot.getId(),null,"Inspección realizada",
            "Revisión y mediciones","Cumple especificación","Continuar mantenimiento",LocalDate.now()),
            pdf("informe.pdf"),List.of());
        assertEquals("ENVIADO",informe.getEstado());
        ConformidadServicio conf=conformidades.registrar(new Iteracion2Forms.Conformidad(
            ot.getId(),"ACT-12-001",LocalDate.now(),"Responsable cliente",null,5,"CONFORME","Sin observaciones"),pdf("acta-conformidad.pdf"));
        assertEquals("CONFORME",conf.getEstadoConformidad());
        assertEquals("ATENDIDA",ordenes.findById(ot.getId()).orElseThrow().getEstado());
        usuario("almacenero");
        herramientaService.registrar(new Iteracion2Forms.MovimientoHerramienta(herramienta.getId(),ot.getId(),tecnico.getId(),
            "DEVOLUCION",BigDecimal.ONE,"BUENO",null));
        assertEquals(0,herramienta.getCantidadDisponible().compareTo(new BigDecimal("3")));
        assertThrows(NegocioException.class,()->materialService.registrar(new Iteracion2Forms.MovimientoMaterial(
            material.getId(),ot.getId(),"SALIDA",new BigDecimal("99"),null,null)));
    }
}
