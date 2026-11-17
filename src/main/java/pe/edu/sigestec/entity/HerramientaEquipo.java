package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "HERRAMIENTA_EQUIPO")
public class HerramientaEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_herramienta_equipo")
    private Long id;
    @Column(name = "codigo_herramienta", length = 30)
    private String codigoHerramienta;
    @Column(name = "nombre", length = 180)
    private String nombre;
    @Column(name = "descripcion", length = 400)
    private String descripcion;
    @Column(name = "cantidad_total", precision = 12, scale = 2)
    private BigDecimal cantidadTotal = BigDecimal.ZERO;
    @Column(name = "cantidad_disponible", precision = 12, scale = 2)
    private BigDecimal cantidadDisponible = BigDecimal.ZERO;
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "estado", length = 1, columnDefinition = "CHAR(1)")
    private String estado = "A";

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getCodigoHerramienta() {
        return codigoHerramienta;
    }

    public void setCodigoHerramienta(String value) {
        this.codigoHerramienta = value;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String value) {
        this.nombre = value;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    public BigDecimal getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(BigDecimal value) {
        this.cantidadTotal = value;
    }

    public BigDecimal getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(BigDecimal value) {
        this.cantidadDisponible = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }
}
