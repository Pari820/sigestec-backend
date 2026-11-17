package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "EJECUCION_SERVICIO")
public class EjecucionServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejecucion_servicio")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_orden_trabajo", nullable = false)
    private OrdenTrabajo ordenTrabajo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;

    @Column(name = "fecha_ejecucion")
    private LocalDateTime fechaEjecucion = LocalDateTime.now();

    @Column(name = "descripcion", length = 1500)
    private String descripcion;

    @Column(name = "mediciones", length = 1500)
    private String mediciones;

    @Column(name = "observaciones", length = 1500)
    private String observaciones;

    @Column(name = "estado", length = 30)
    private String estado = "REGISTRADA";

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public OrdenTrabajo getOrdenTrabajo() { return ordenTrabajo; }
    public void setOrdenTrabajo(OrdenTrabajo value) { this.ordenTrabajo = value; }

    public Empleado getEmpleadoRegistra() { return empleadoRegistra; }
    public void setEmpleadoRegistra(Empleado value) { this.empleadoRegistra = value; }

    public LocalDateTime getFechaEjecucion() { return fechaEjecucion; }
    public void setFechaEjecucion(LocalDateTime value) { this.fechaEjecucion = value; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String value) { this.descripcion = value; }

    public String getMediciones() { return mediciones; }
    public void setMediciones(String value) { this.mediciones = value; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }

    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }

}
