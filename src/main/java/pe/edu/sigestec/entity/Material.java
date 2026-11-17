package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "MATERIAL")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Long id;

    @Column(name = "codigo", length = 30)
    private String codigo;

    @Column(name = "nombre", length = 180)
    private String nombre;

    @Column(name = "unidad_medida", length = 30)
    private String unidadMedida;

    @Column(name = "stock_actual", precision = 12, scale = 2)
    private BigDecimal stockActual = BigDecimal.ZERO;

    @Column(name = "stock_minimo", precision = 12, scale = 2)
    private BigDecimal stockMinimo = BigDecimal.ZERO;

    @Column(name = "estado", length = 1)
    private String estado = "A";

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String value) { this.codigo = value; }

    public String getNombre() { return nombre; }
    public void setNombre(String value) { this.nombre = value; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String value) { this.unidadMedida = value; }

    public BigDecimal getStockActual() { return stockActual; }
    public void setStockActual(BigDecimal value) { this.stockActual = value; }

    public BigDecimal getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(BigDecimal value) { this.stockMinimo = value; }

    public String getEstado() { return estado; }
    public void setEstado(String value) { this.estado = value; }

}
