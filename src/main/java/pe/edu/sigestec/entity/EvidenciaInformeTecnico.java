package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "EVIDENCIA_INFORME_TECNICO")
public class EvidenciaInformeTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evidencia_informe_tecnico")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_informe_tecnico", nullable = false)
    private InformeTecnico informeTecnico;

    @Column(name = "url_archivo", length = 1000)
    private String urlArchivo;

    @Column(name = "nombre_archivo", length = 250)
    private String nombreArchivo;

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public InformeTecnico getInformeTecnico() { return informeTecnico; }
    public void setInformeTecnico(InformeTecnico value) { this.informeTecnico = value; }

    public String getUrlArchivo() { return urlArchivo; }
    public void setUrlArchivo(String value) { this.urlArchivo = value; }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String value) { this.nombreArchivo = value; }

}
