package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "REPRESENTANTE_EMPRESA")
public class RepresentanteEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_representante")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_persona_natural", nullable = false)
    private PersonaNatural personaNatural;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_persona_juridica", nullable = false)
    private PersonaJuridica personaJuridica;
    @Column(name = "cargo_representante", length = 120)
    private String cargoRepresentante;
    @Column(name = "tipo_representante", length = 30)
    private String tipoRepresentante = "CONTACTO";
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "estado", length = 1, columnDefinition = "CHAR(1)")
    private String estado = "A";

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public PersonaNatural getPersonaNatural() {
        return personaNatural;
    }

    public void setPersonaNatural(PersonaNatural value) {
        this.personaNatural = value;
    }

    public PersonaJuridica getPersonaJuridica() {
        return personaJuridica;
    }

    public void setPersonaJuridica(PersonaJuridica value) {
        this.personaJuridica = value;
    }

    public String getCargoRepresentante() {
        return cargoRepresentante;
    }

    public void setCargoRepresentante(String value) {
        this.cargoRepresentante = value;
    }

    public String getTipoRepresentante() {
        return tipoRepresentante;
    }

    public void setTipoRepresentante(String value) {
        this.tipoRepresentante = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }
}
