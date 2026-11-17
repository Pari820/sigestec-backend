package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "SOLICITUD_SERVICIO")
public class SolicitudServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_persona_juridica", nullable = false)
    private PersonaJuridica empresaCliente;
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_representante_solicitante", nullable = true)
    private RepresentanteEmpresa representanteSolicitante;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_tipo_servicio", nullable = false)
    private TipoServicio tipoServicio;
    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud = LocalDate.now();
    @Column(name = "fecha_requerida")
    private LocalDate fechaRequerida;
    @Column(name = "descripcion", length = 1000)
    private String descripcion;
    @Column(name = "lugar_servicio", length = 300)
    private String lugarServicio;
    @Column(name = "observaciones", length = 1000)
    private String observaciones;
    @Column(name = "prioridad", length = 255)
    private String prioridad = "MEDIA";
    @Column(name = "estado", length = 20)
    private String estado = "PENDIENTE";
    @Column(name = "url_archivo_adjunto", length = 1000)
    private String urlArchivoAdjunto;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public PersonaJuridica getEmpresaCliente() {
        return empresaCliente;
    }

    public void setEmpresaCliente(PersonaJuridica value) {
        this.empresaCliente = value;
    }

    public RepresentanteEmpresa getRepresentanteSolicitante() {
        return representanteSolicitante;
    }

    public void setRepresentanteSolicitante(RepresentanteEmpresa value) {
        this.representanteSolicitante = value;
    }

    public Empleado getEmpleadoRegistra() {
        return empleadoRegistra;
    }

    public void setEmpleadoRegistra(Empleado value) {
        this.empleadoRegistra = value;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio value) {
        this.tipoServicio = value;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate value) {
        this.fechaSolicitud = value;
    }

    public LocalDate getFechaRequerida() {
        return fechaRequerida;
    }

    public void setFechaRequerida(LocalDate value) {
        this.fechaRequerida = value;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    public String getLugarServicio() {
        return lugarServicio;
    }

    public void setLugarServicio(String value) {
        this.lugarServicio = value;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String value) {
        this.observaciones = value;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String value) {
        this.prioridad = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }

    public String getUrlArchivoAdjunto() {
        return urlArchivoAdjunto;
    }

    public void setUrlArchivoAdjunto(String value) {
        this.urlArchivoAdjunto = value;
    }
}
