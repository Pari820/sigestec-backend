package pe.edu.sigestec.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.*;

@Entity
@Table(name = "EMPLEADO")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_persona_natural", nullable = false)
    private PersonaNatural personaNatural;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo cargo;
    @Column(name = "codigo_empleado", length = 20)
    private String codigoEmpleado;
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso = LocalDate.now();
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

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo value) {
        this.cargo = value;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String value) {
        this.codigoEmpleado = value;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate value) {
        this.fechaIngreso = value;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String value) {
        this.estado = value;
    }
}
