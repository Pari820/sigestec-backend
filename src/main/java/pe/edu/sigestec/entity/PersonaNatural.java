package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "PERSONA_NATURAL")
public class PersonaNatural {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona_natural")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;
    @Column(name = "dni", length = 8)
    private String dni;
    @Column(name = "nombres", length = 100)
    private String nombres;
    @Column(name = "apellidos", length = 120)
    private String apellidos;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String value) {
        this.dni = value;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String value) {
        this.nombres = value;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String value) {
        this.apellidos = value;
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
