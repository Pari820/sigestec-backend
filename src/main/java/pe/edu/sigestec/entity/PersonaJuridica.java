package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "PERSONA_JURIDICA")
public class PersonaJuridica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona_juridica")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;
    @Column(name = "ruc", length = 11)
    private String ruc;
    @Column(name = "razon_social", length = 180)
    private String razonSocial;
    @Column(name = "direccion", length = 250)
    private String direccion;
    @Column(name = "telefono", length = 20)
    private String telefono;
    @Column(name = "correo", length = 150)
    private String correo;
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "estado", length = 1, columnDefinition = "CHAR(1)")
    private String estado = "A";

    public Long getId() {
        return id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil value) {
        this.perfil = value;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String value) {
        this.ruc = value;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String value) {
        this.razonSocial = value;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String value) {
        this.direccion = value;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String value) {
        this.telefono = value;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String value) {
        this.correo = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }
}
