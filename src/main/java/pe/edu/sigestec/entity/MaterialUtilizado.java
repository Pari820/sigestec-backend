package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "MATERIAL_UTILIZADO")
public class MaterialUtilizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material_utilizado")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_ejecucion_servicio", nullable = false)
    private EjecucionServicio ejecucionServicio;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_material", nullable = false)
    private Material material;

    @Column(name = "cantidad_real", precision = 12, scale = 2)
    private BigDecimal cantidadReal;

    @Column(name = "estado_validacion", length = 25)
    private String estadoValidacion = "PENDIENTE";

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public EjecucionServicio getEjecucionServicio() { return ejecucionServicio; }
    public void setEjecucionServicio(EjecucionServicio value) { this.ejecucionServicio = value; }

    public Material getMaterial() { return material; }
    public void setMaterial(Material value) { this.material = value; }

    public BigDecimal getCantidadReal() { return cantidadReal; }
    public void setCantidadReal(BigDecimal value) { this.cantidadReal = value; }

    public String getEstadoValidacion() { return estadoValidacion; }
    public void setEstadoValidacion(String value) { this.estadoValidacion = value; }

}
