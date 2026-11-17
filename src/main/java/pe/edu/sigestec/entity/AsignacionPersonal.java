package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "ASIGNACION_PERSONAL")
public class AsignacionPersonal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_orden_trabajo", nullable = false)
    private OrdenTrabajo ordenTrabajo;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;
    @Column(name = "funcion_servicio", length = 120)
    private String funcionServicio;
    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion = LocalDate.now();
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "estado", length = 1, columnDefinition = "CHAR(1)")
    private String estado = "A";

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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado value) {
        this.empleado = value;
    }

    public String getFuncionServicio() {
        return funcionServicio;
    }

    public void setFuncionServicio(String value) {
        this.funcionServicio = value;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate value) {
        this.fechaAsignacion = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }
}
