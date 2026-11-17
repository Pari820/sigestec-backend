package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "EVIDENCIA_EJECUCION")
public class EvidenciaEjecucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evidencia_ejecucion")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_ejecucion_servicio", nullable = false)
    private EjecucionServicio ejecucionServicio;

    @Column(name = "etapa", length = 20)
    private String etapa;

    @Column(name = "url_archivo", length = 1000)
    private String urlArchivo;

    @Column(name = "nombre_archivo", length = 250)
    private String nombreArchivo;

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public EjecucionServicio getEjecucionServicio() { return ejecucionServicio; }
    public void setEjecucionServicio(EjecucionServicio value) { this.ejecucionServicio = value; }

    public String getEtapa() { return etapa; }
    public void setEtapa(String value) { this.etapa = value; }

    public String getUrlArchivo() { return urlArchivo; }
    public void setUrlArchivo(String value) { this.urlArchivo = value; }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String value) { this.nombreArchivo = value; }

}
