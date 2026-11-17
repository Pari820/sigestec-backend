package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "ORDEN_COMPRA")
public class OrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_compra")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_cotizacion", nullable = false)
    private Cotizacion cotizacion;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_persona_juridica", nullable = false)
    private PersonaJuridica empresaCliente;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_representante_emisor", nullable = true)
    private RepresentanteEmpresa representanteEmisor;
    @Column(name = "numero_orden_compra", length = 40)
    private String numeroOrdenCompra;
    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;
    @Column(name = "monto_total", precision = 12, scale = 2)
    private BigDecimal montoTotal;
    @Column(name = "url_archivo", length = 1000)
    private String urlArchivo;
    @Column(name = "estado", length = 20)
    private String estado = "REGISTRADA";

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion value) {
        this.cotizacion = value;
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

    public RepresentanteEmpresa getRepresentanteEmisor() {
        return representanteEmisor;
    }

    public void setRepresentanteEmisor(RepresentanteEmpresa value) {
        this.representanteEmisor = value;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String value) {
        this.numeroOrdenCompra = value;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate value) {
        this.fechaEmision = value;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal value) {
        this.montoTotal = value;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String value) {
        this.urlArchivo = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }
}
