package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "APROBACION_ATS")
public class AprobacionAts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aprobacion_ats")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_analisis_trabajo_seguro", nullable = false)
    private AnalisisTrabajoSeguro analisisTrabajoSeguro;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_empleado_registra", nullable = false)
    private Empleado empleadoRegistra;

    @Column(name = "fecha_revision")
    private LocalDate fechaRevision = LocalDate.now();

    @Column(name = "supervisor_cliente", length = 300)
    private String supervisorCliente;

    @Column(name = "ssoma_cliente", length = 300)
    private String ssomaCliente;

    @Column(name = "resultado", length = 20)
    private String resultado;

    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    @Column(name = "url_acta", length = 1000)
    private String urlActa;

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public AnalisisTrabajoSeguro getAnalisisTrabajoSeguro() { return analisisTrabajoSeguro; }
    public void setAnalisisTrabajoSeguro(AnalisisTrabajoSeguro value) { this.analisisTrabajoSeguro = value; }

    public Empleado getEmpleadoRegistra() { return empleadoRegistra; }
    public void setEmpleadoRegistra(Empleado value) { this.empleadoRegistra = value; }

    public LocalDate getFechaRevision() { return fechaRevision; }
    public void setFechaRevision(LocalDate value) { this.fechaRevision = value; }

    public String getSupervisorCliente() { return supervisorCliente; }
    public void setSupervisorCliente(String value) { this.supervisorCliente = value; }

    public String getSsomaCliente() { return ssomaCliente; }
    public void setSsomaCliente(String value) { this.ssomaCliente = value; }

    public String getResultado() { return resultado; }
    public void setResultado(String value) { this.resultado = value; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String value) { this.observaciones = value; }

    public String getUrlActa() { return urlActa; }
    public void setUrlActa(String value) { this.urlActa = value; }

}
