package pe.edu.sigestec;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mock.web.MockMultipartFile;
import pe.edu.sigestec.dto.Forms;
import pe.edu.sigestec.service.*;
import pe.edu.sigestec.repository.*;
import pe.edu.sigestec.entity.*;
import pe.edu.sigestec.exception.NegocioException;

import java.math.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integración de CU01-CU06 con H2. No incorporan CU07.
 */
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:sigestec_cu_test;MODE=Oracle;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "app.demo.enabled=true",
        "app.storage.directory=./archivos-sigestec-test"
})
@Transactional
class Iteracion1Test {
    @Autowired
    SolicitudServicioService solicitudes;
    @Autowired
    CotizacionService cotizaciones;
    @Autowired
    OrdenCompraService compras;
    @Autowired
    AsignacionPersonalService asignaciones;
    @Autowired
    OrdenTrabajoService programacion;
    @Autowired
    AnalisisTrabajoSeguroService analisis;
    @Autowired
    PersonaJuridicaRepository empresas;
    @Autowired
    TipoServicioRepository tipos;
    @Autowired
    EmpleadoRepository empleados;
    @Autowired
    CotizacionRepository cotizacionesRepo;
    @Autowired
    DetalleCotizacionRepository detallesCotizacion;
    @Autowired
    DetalleOrdenCompraRepository detallesCompra;
    @Autowired
    AsignacionPersonalRepository asignacionesRepo;
    @Autowired
    DocumentoPlanificacionRepository documentos;
    @Autowired
    DetalleAnalisisRiesgoRepository detallesRiesgo;
    @Autowired
    AnalisisTrabajoSeguroRepository analisisRepo;

    private void usuario(String nombre) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(nombre, null, Collections.emptyList()));
    }

    @AfterEach
    void liberarSesion() {
        SecurityContextHolder.clearContext();
    }

    private Forms.CotizacionForm form(Long solicitudId, String descripcion) {
        return new Forms.CotizacionForm(solicitudId, descripcion, "Observación",
                List.of(new Forms.DetalleCotizacionForm(descripcion, BigDecimal.valueOf(2), new BigDecimal("100.00"), "SERVICIO")));
    }

    private SolicitudServicio solicitud() {
        usuario("gerente");
        return solicitudes.registrar(new Forms.SolicitudForm(
                empresas.findAll().get(0).getId(), tipos.findAll().get(0).getId(),
                "Revisión de tableros eléctricos", null, null, null));
    }

    private static MockMultipartFile pdf(String nombre) {
        return new MockMultipartFile("pdf", nombre, "application/pdf", "%PDF-1.4\n%%EOF".getBytes());
    }

    @Test
    void solicitudPermiteVariasCotizacionesConDetalleYUnSoloAceptado() {
        SolicitudServicio s = solicitud();
        Cotizacion a = cotizaciones.registrar(form(s.getId(), "Alternativa A"));
        Cotizacion b = cotizaciones.registrar(form(s.getId(), "Alternativa B"));
        assertEquals(2, cotizacionesRepo.findBySolicitudServicioId(s.getId()).size());
        assertEquals(1, detallesCotizacion.findByCotizacionIdOrderByItemAsc(a.getId()).size());
        assertEquals(new BigDecimal("236.00"), a.getTotal());
        cotizaciones.cambiarEstado(a.getId(), "ACEPTADA");
        assertThrows(NegocioException.class, () -> cotizaciones.cambiarEstado(b.getId(), "ACEPTADA"));
    }

    @Test
    void flujoCompletoHastaAnalisisTrabajoSeguroNormalizado() {
        SolicitudServicio s = solicitud();
        Cotizacion c = cotizaciones.registrar(form(s.getId(), "Mantenimiento preventivo"));
        cotizaciones.cambiarEstado(c.getId(), "ACEPTADA");
        OrdenCompra oc = compras.registrar(c.getId(), "OC-TEST-001", LocalDate.now(), c.getTotal(), pdf("oc.pdf"), null);
        assertTrue(oc.getUrlArchivo().contains("/api/v1/archivos/"));
        assertEquals(1, detallesCompra.findByOrdenCompraIdOrderByItemAsc(oc.getId()).size());
        List<Empleado> tech = empleados.findAll().stream().filter(e -> "TECNICO".equals(e.getCargo().getNombre())).toList();
        Empleado ssoma = empleados.findAll().stream().filter(e -> "SSOMA".equals(e.getCargo().getNombre())).findFirst().orElseThrow();
        usuario("supervisor");
        OrdenTrabajo ot = asignaciones.registrar(new Forms.AsignacionForm(s.getId(), tech.get(0).getId(),
                ssoma.getId(), null, List.of(tech.get(1).getId())));
        assertEquals(3, asignacionesRepo.findByOrdenTrabajoIdAndEstado(ot.getId(), "A").size());
        LocalDateTime inicio = LocalDateTime.now().plusDays(3).withSecond(0).withNano(0);
        programacion.programar(ot.getId(), inicio, inicio.plusHours(3), "Sala de tableros",
                LocalDate.now(), true, pdf("pt.pdf"), pdf("iper.pdf"), null, null);
        assertEquals(2, documentos.findByOrdenTrabajoId(ot.getId()).size());
        usuario("ssoma");
        AnalisisTrabajoSeguro a = analisis.elaborar(new Forms.AtsForm(ot.getId(), "Área señalizada",
                List.of("Contacto eléctrico"), List.of("Guantes aislantes"), "Multímetro",
                List.of(new Forms.AtsEtapa("Inspección", "Tablero energizado", "Electrocución", "ALTO", "Desenergizar el área"))));
        assertEquals("PENDIENTE", a.getEstado()); // CU07 no implementado
        assertEquals(1, detallesRiesgo.findByAnalisisTrabajoSeguroId(a.getId()).size());
        assertEquals(1, analisisRepo.count());
    }
}
