package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "ORDEN_TRABAJO")
public class OrdenTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_trabajo")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_orden_compra", nullable = false)
    private OrdenCompra ordenCompra;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_persona_juridica", nullable = false)
    private PersonaJuridica empresaCliente;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;
    @Column(name = "numero_orden_trabajo", length = 30)
    private String numeroOrdenTrabajo;
    @Column(name = "fecha_programada_inicio")
    private LocalDateTime fechaProgramadaInicio;
    @Column(name = "fecha_programada_fin")
    private LocalDateTime fechaProgramadaFin;
    @Column(name = "fecha_coordinacion_cliente")
    private LocalDateTime fechaCoordinacionCliente;
    @Column(name = "lugar_trabajo", length = 300)
    private String lugarTrabajo;
    @Column(name = "descripcion", length = 1000)
    private String descripcion;
    @Column(name = "estado", length = 25)
    private String estado = "PENDIENTE";

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra value) {
        this.ordenCompra = value;
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

    public String getNumeroOrdenTrabajo() {
        return numeroOrdenTrabajo;
    }

    public void setNumeroOrdenTrabajo(String value) {
        this.numeroOrdenTrabajo = value;
    }

    public LocalDateTime getFechaProgramadaInicio() {
        return fechaProgramadaInicio;
    }

    public void setFechaProgramadaInicio(LocalDateTime value) {
        this.fechaProgramadaInicio = value;
    }

    public LocalDateTime getFechaProgramadaFin() {
        return fechaProgramadaFin;
    }

    public void setFechaProgramadaFin(LocalDateTime value) {
        this.fechaProgramadaFin = value;
    }

    public LocalDateTime getFechaCoordinacionCliente() {
        return fechaCoordinacionCliente;
    }

    public void setFechaCoordinacionCliente(LocalDateTime value) {
        this.fechaCoordinacionCliente = value;
    }

    public String getLugarTrabajo() {
        return lugarTrabajo;
    }

    public void setLugarTrabajo(String value) {
        this.lugarTrabajo = value;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }
}
