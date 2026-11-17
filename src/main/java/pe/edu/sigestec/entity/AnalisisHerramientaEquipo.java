package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "ANALISIS_HERRAMIENTA_EQUIPO")
public class AnalisisHerramientaEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_analisis_herramienta_equipo")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_analisis_trabajo_seguro", nullable = false)
    private AnalisisTrabajoSeguro analisisTrabajoSeguro;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_herramienta_equipo", nullable = false)
    private HerramientaEquipo herramientaEquipo;
    @Column(name = "cantidad_requerida", precision = 12, scale = 2)
    private BigDecimal cantidadRequerida = BigDecimal.ONE;
    @Column(name = "observaciones", length = 1000)
    private String observaciones;

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

    public HerramientaEquipo getHerramientaEquipo() {
        return herramientaEquipo;
    }

    public void setHerramientaEquipo(HerramientaEquipo value) {
        this.herramientaEquipo = value;
    }

    public BigDecimal getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(BigDecimal value) {
        this.cantidadRequerida = value;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String value) {
        this.observaciones = value;
    }
}
