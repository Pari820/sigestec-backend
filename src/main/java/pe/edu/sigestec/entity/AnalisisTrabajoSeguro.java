package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "ANALISIS_TRABAJO_SEGURO")
public class AnalisisTrabajoSeguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_analisis_trabajo_seguro")
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
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_elabora", nullable = false)
    private Empleado empleadoElabora;
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro = LocalDate.now();
    @Column(name = "condiciones_area", length = 1500)
    private String condicionesArea;
    @Column(name = "estado", length = 20)
    private String estado = "PENDIENTE";
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

    public Empleado getEmpleadoElabora() {
        return empleadoElabora;
    }

    public void setEmpleadoElabora(Empleado value) {
        this.empleadoElabora = value;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate value) {
        this.fechaRegistro = value;
    }

    public String getCondicionesArea() {
        return condicionesArea;
    }

    public void setCondicionesArea(String value) {
        this.condicionesArea = value;
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
