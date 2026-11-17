package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.ConformidadServicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConformidadServicioRepository extends JpaRepository<ConformidadServicio, Long> {
    boolean existsByOrdenTrabajoId(Long id);
}
