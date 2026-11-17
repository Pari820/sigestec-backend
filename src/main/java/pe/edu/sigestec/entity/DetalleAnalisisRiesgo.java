package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "DETALLE_ANALISIS_RIESGO")
public class DetalleAnalisisRiesgo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_analisis_riesgo")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_analisis_trabajo_seguro", nullable = false)
    private AnalisisTrabajoSeguro analisisTrabajoSeguro;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_riesgo", nullable = false)
    private Riesgo riesgo;
    @Column(name = "etapa_trabajo", length = 300)
    private String etapaTrabajo;
    @Column(name = "actividad", length = 300)
    private String actividad;
    @Column(name = "peligro", length = 300)
    private String peligro;
    @Column(name = "medida_control", length = 500)
    private String medidaControl;
    @Column(name = "probabilidad")
    private Integer probabilidad;
    @Column(name = "severidad")
    private Integer severidad;
    @Column(name = "nivel_riesgo", length = 30)
    private String nivelRiesgo;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public AnalisisTrabajoSeguro getAnalisisTrabajoSeguro() {
        return analisisTrabajoSeguro;
    }

    public void setAnalisisTrabajoSeguro(AnalisisTrabajoSeguro value) {
        this.analisisTrabajoSeguro = value;
    }

    public Riesgo getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(Riesgo value) {
        this.riesgo = value;
    }

    public String getEtapaTrabajo() {
        return etapaTrabajo;
    }

    public void setEtapaTrabajo(String value) {
        this.etapaTrabajo = value;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String value) {
        this.actividad = value;
    }

    public String getPeligro() {
        return peligro;
    }

    public void setPeligro(String value) {
        this.peligro = value;
    }

    public String getMedidaControl() {
        return medidaControl;
    }

    public void setMedidaControl(String value) {
        this.medidaControl = value;
    }

    public Integer getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(Integer value) {
        this.probabilidad = value;
    }

    public Integer getSeveridad() {
        return severidad;
    }

    public void setSeveridad(Integer value) {
        this.severidad = value;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String value) {
        this.nivelRiesgo = value;
    }
}
