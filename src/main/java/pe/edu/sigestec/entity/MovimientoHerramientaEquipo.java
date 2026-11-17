package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "MOVIMIENTO_HERRAMIENTA_EQUIPO")
public class MovimientoHerramientaEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento_herramienta_equipo")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_herramienta_equipo", nullable = false)
    private HerramientaEquipo herramientaEquipo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_orden_trabajo")
    private OrdenTrabajo ordenTrabajo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_responsable")
    private Empleado empleadoResponsable;

    @Column(name = "tipo_movimiento", length = 20)
    private String tipoMovimiento;

    @Column(name = "cantidad", precision = 12, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "fecha_movimiento")
    private LocalDateTime fechaMovimiento = LocalDateTime.now();

    @Column(name = "estado_devolucion", length = 20)
    private String estadoDevolucion;

    @Column(name = "observacion", length = 1000)
    private String observacion;

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public HerramientaEquipo getHerramientaEquipo() { return herramientaEquipo; }
    public void setHerramientaEquipo(HerramientaEquipo value) { this.herramientaEquipo = value; }

    public OrdenTrabajo getOrdenTrabajo() { return ordenTrabajo; }
    public void setOrdenTrabajo(OrdenTrabajo value) { this.ordenTrabajo = value; }

    public Empleado getEmpleadoRegistra() { return empleadoRegistra; }
    public void setEmpleadoRegistra(Empleado value) { this.empleadoRegistra = value; }

    public Empleado getEmpleadoResponsable() { return empleadoResponsable; }
    public void setEmpleadoResponsable(Empleado value) { this.empleadoResponsable = value; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String value) { this.tipoMovimiento = value; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal value) { this.cantidad = value; }

    public LocalDateTime getFechaMovimiento() { return fechaMovimiento; }
    public void setFechaMovimiento(LocalDateTime value) { this.fechaMovimiento = value; }

    public String getEstadoDevolucion() { return estadoDevolucion; }
    public void setEstadoDevolucion(String value) { this.estadoDevolucion = value; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String value) { this.observacion = value; }

}
