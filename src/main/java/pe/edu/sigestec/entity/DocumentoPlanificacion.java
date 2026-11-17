package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "DOCUMENTO_PLANIFICACION")
public class DocumentoPlanificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento_planificacion")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_orden_trabajo", nullable = false)
    private OrdenTrabajo ordenTrabajo;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_persona_juridica", nullable = false)
    private PersonaJuridica empresaCliente;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;
    @Column(name = "tipo_documento", length = 30)
    private String tipoDocumento;
    @Column(name = "nombre_documento", length = 200)
    private String nombreDocumento;
    @Column(name = "url_archivo", length = 1000)
    private String urlArchivo;
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    @Column(name = "fecha_envio_cliente")
    private LocalDateTime fechaEnvioCliente;
    @Column(name = "estado", length = 20)
    private String estado = "REGISTRADO";
    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo value) {
        this.ordenTrabajo = value;
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

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String value) {
        this.nombreDocumento = value;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String value) {
        this.urlArchivo = value;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime value) {
        this.fechaRegistro = value;
    }

    public LocalDateTime getFechaEnvioCliente() {
        return fechaEnvioCliente;
    }

    public void setFechaEnvioCliente(LocalDateTime value) {
        this.fechaEnvioCliente = value;
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
}
