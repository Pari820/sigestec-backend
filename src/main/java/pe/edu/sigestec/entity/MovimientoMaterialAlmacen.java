package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "MOVIMIENTO_MATERIAL_ALMACEN")
public class MovimientoMaterialAlmacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento_material_almacen")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_material", nullable = false)
    private Material material;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_orden_trabajo")
    private OrdenTrabajo ordenTrabajo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;

    @Column(name = "tipo_movimiento", length = 20)
    private String tipoMovimiento;

    @Column(name = "cantidad", precision = 12, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "fecha_movimiento")
    private LocalDateTime fechaMovimiento = LocalDateTime.now();

    @Column(name = "motivo", length = 400)
    private String motivo;

    @Column(name = "observacion", length = 1000)
    private String observacion;

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public Material getMaterial() { return material; }
    public void setMaterial(Material value) { this.material = value; }

    public OrdenTrabajo getOrdenTrabajo() { return ordenTrabajo; }
    public void setOrdenTrabajo(OrdenTrabajo value) { this.ordenTrabajo = value; }

    public Empleado getEmpleadoRegistra() { return empleadoRegistra; }
    public void setEmpleadoRegistra(Empleado value) { this.empleadoRegistra = value; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String value) { this.tipoMovimiento = value; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal value) { this.cantidad = value; }

    public LocalDateTime getFechaMovimiento() { return fechaMovimiento; }
    public void setFechaMovimiento(LocalDateTime value) { this.fechaMovimiento = value; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String value) { this.motivo = value; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String value) { this.observacion = value; }

}
