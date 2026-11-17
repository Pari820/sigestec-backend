package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "DETALLE_ORDEN_COMPRA")
public class DetalleOrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_orden_compra")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_orden_compra", nullable = false)
    private OrdenCompra ordenCompra;
    @Column(name = "item")
    private Integer item;
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    @Column(name = "cantidad", precision = 12, scale = 2)
    private BigDecimal cantidad;
    @Column(name = "unidad_medida", length = 30)
    private String unidadMedida;
    @Column(name = "precio_unitario", precision = 12, scale = 2)
    private BigDecimal precioUnitario;
    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal;

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra value) {
        this.ordenCompra = value;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer value) {
        this.item = value;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal value) {
        this.cantidad = value;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String value) {
        this.unidadMedida = value;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal value) {
        this.precioUnitario = value;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal value) {
        this.subtotal = value;
    }
}
