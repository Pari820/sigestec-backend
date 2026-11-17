package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "COTIZACION")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_solicitud", nullable = false)
    private SolicitudServicio solicitudServicio;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_persona_juridica", nullable = false)
    private PersonaJuridica empresaCliente;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_representante_respuesta", nullable = true)
    private RepresentanteEmpresa representanteRespuesta;
    @Column(name = "numero_cotizacion", length = 30)
    private String numeroCotizacion;
    @Column(name = "fecha_emision")
    private LocalDate fechaEmision = LocalDate.now();
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
    @Column(name = "fecha_respuesta")
    private LocalDate fechaRespuesta;
    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;
    @Column(name = "igv", precision = 12, scale = 2)
    private BigDecimal igv = BigDecimal.ZERO;
    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;
    @Column(name = "estado", length = 20)
    private String estado = "BORRADOR";
    @Column(name = "observaciones", length = 1000)
    private String observaciones;
    @Column(name = "url_archivo", length = 1000)
    private String urlArchivo;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public SolicitudServicio getSolicitudServicio() {
        return solicitudServicio;
    }

    public void setSolicitudServicio(SolicitudServicio value) {
        this.solicitudServicio = value;
    }

    public PersonaJuridica getEmpresaCliente() {
        return empresaCliente;
    }

    public void setEmpresaCliente(PersonaJuridica value) {
        this.empresaCliente = value;
    }

    public Empleado getEmpleadoRegistra() {
        return empleadoRegistra;
    }

    public void setEmpleadoRegistra(Empleado value) {
        this.empleadoRegistra = value;
    }

    public RepresentanteEmpresa getRepresentanteRespuesta() {
        return representanteRespuesta;
    }

    public void setRepresentanteRespuesta(RepresentanteEmpresa value) {
        this.representanteRespuesta = value;
    }

    public String getNumeroCotizacion() {
        return numeroCotizacion;
    }

    public void setNumeroCotizacion(String value) {
        this.numeroCotizacion = value;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate value) {
        this.fechaEmision = value;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate value) {
        this.fechaVencimiento = value;
    }

    public LocalDate getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDate value) {
        this.fechaRespuesta = value;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal value) {
        this.subtotal = value;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal value) {
        this.igv = value;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal value) {
        this.total = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String value) {
        this.observaciones = value;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String value) {
        this.urlArchivo = value;
    }
}
