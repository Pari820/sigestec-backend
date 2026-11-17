package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "INFORME_TECNICO")
public class InformeTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informe_tecnico")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_orden_trabajo", nullable = false)
    private OrdenTrabajo ordenTrabajo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_ejecucion_servicio", nullable = false)
    private EjecucionServicio ejecucionServicio;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;

    @Column(name = "numero_informe", length = 40)
    private String numeroInforme;

    @Column(name = "fecha_informe")
    private LocalDate fechaInforme = LocalDate.now();

    @Column(name = "fecha_envio")
    private LocalDate fechaEnvio;

    @Column(name = "diagnostico", length = 1500)
    private String diagnostico;

    @Column(name = "trabajos_realizados", length = 2000)
    private String trabajosRealizados;

    @Column(name = "conclusiones", length = 1500)
    private String conclusiones;

    @Column(name = "recomendaciones", length = 1500)
    private String recomendaciones;

    @Column(name = "url_archivo", length = 1000)
    private String urlArchivo;

    @Column(name = "estado", length = 25)
    private String estado = "REGISTRADA";

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public OrdenTrabajo getOrdenTrabajo() { return ordenTrabajo; }
    public void setOrdenTrabajo(OrdenTrabajo value) { this.ordenTrabajo = value; }

    public EjecucionServicio getEjecucionServicio() { return ejecucionServicio; }
    public void setEjecucionServicio(EjecucionServicio value) { this.ejecucionServicio = value; }

    public Empleado getEmpleadoRegistra() { return empleadoRegistra; }
    public void setEmpleadoRegistra(Empleado value) { this.empleadoRegistra = value; }

    public String getNumeroInforme() { return numeroInforme; }
    public void setNumeroInforme(String value) { this.numeroInforme = value; }

    public LocalDate getFechaInforme() { return fechaInforme; }
    public void setFechaInforme(LocalDate value) { this.fechaInforme = value; }

    public LocalDate getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDate value) { this.fechaEnvio = value; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String value) { this.diagnostico = value; }

    public String getTrabajosRealizados() { return trabajosRealizados; }
    public void setTrabajosRealizados(String value) { this.trabajosRealizados = value; }

    public String getConclusiones() { return conclusiones; }
    public void setConclusiones(String value) { this.conclusiones = value; }

    public String getRecomendaciones() { return recomendaciones; }
    public void setRecomendaciones(String value) { this.recomendaciones = value; }

    public String getUrlArchivo() { return urlArchivo; }
    public void setUrlArchivo(String value) { this.urlArchivo = value; }

    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }

}
