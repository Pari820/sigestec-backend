package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "ANALISIS_EQUIPO_PROTECCION_PERSONAL")
public class AnalisisEquipoProteccionPersonal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_analisis_equipo_proteccion")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_analisis_trabajo_seguro", nullable = false)
    private AnalisisTrabajoSeguro analisisTrabajoSeguro;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_equipo_proteccion_personal", nullable = false)
    private EquipoProteccionPersonal equipoProteccionPersonal;
    @Column(name = "cantidad", precision = 12, scale = 2)
    private BigDecimal cantidad = BigDecimal.ONE;
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

    public EquipoProteccionPersonal getEquipoProteccionPersonal() {
        return equipoProteccionPersonal;
    }

    public void setEquipoProteccionPersonal(EquipoProteccionPersonal value) {
        this.equipoProteccionPersonal = value;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal value) {
        this.cantidad = value;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String value) {
        this.observaciones = value;
    }
}
