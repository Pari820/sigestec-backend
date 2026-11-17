package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "CONFORMIDAD_SERVICIO")
public class ConformidadServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conformidad_servicio")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_orden_trabajo", nullable = false)
    private OrdenTrabajo ordenTrabajo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_informe_tecnico", nullable = false)
    private InformeTecnico informeTecnico;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_representante_empresa")
    private RepresentanteEmpresa representanteEmpresa;

    @Column(name = "nombre_representante", length = 250)
    private String nombreRepresentante;

    @Column(name = "numero_acta", length = 50)
    private String numeroActa;

    @Column(name = "fecha_conformidad")
    private LocalDate fechaConformidad;

    @Column(name = "valoracion")
    private Integer valoracion;

    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    @Column(name = "estado_conformidad", length = 30)
    private String estadoConformidad;

    @Column(name = "url_archivo", length = 1000)
    private String urlArchivo;

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public OrdenTrabajo getOrdenTrabajo() { return ordenTrabajo; }
    public void setOrdenTrabajo(OrdenTrabajo value) { this.ordenTrabajo = value; }

    public InformeTecnico getInformeTecnico() { return informeTecnico; }
    public void setInformeTecnico(InformeTecnico value) { this.informeTecnico = value; }

    public Empleado getEmpleadoRegistra() { return empleadoRegistra; }
    public void setEmpleadoRegistra(Empleado value) { this.empleadoRegistra = value; }

    public RepresentanteEmpresa getRepresentanteEmpresa() { return representanteEmpresa; }
    public void setRepresentanteEmpresa(RepresentanteEmpresa value) { this.representanteEmpresa = value; }

    public String getNombreRepresentante() { return nombreRepresentante; }
    public void setNombreRepresentante(String value) { this.nombreRepresentante = value; }

    public String getNumeroActa() { return numeroActa; }
    public void setNumeroActa(String value) { this.numeroActa = value; }

    public LocalDate getFechaConformidad() { return fechaConformidad; }
    public void setFechaConformidad(LocalDate value) { this.fechaConformidad = value; }

    public Integer getValoracion() { return valoracion; }
    public void setValoracion(Integer value) { this.valoracion = value; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }

    public String getEstadoConformidad() { return estadoConformidad; }
    public void setEstadoConformidad(String value) { this.estadoConformidad = value; }

    public String getUrlArchivo() { return urlArchivo; }
    public void setUrlArchivo(String value) { this.urlArchivo = value; }

}
