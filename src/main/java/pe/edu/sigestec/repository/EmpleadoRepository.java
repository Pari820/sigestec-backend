package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    java.util.Optional<Empleado> findByPersonaNaturalId(Long id);
}
