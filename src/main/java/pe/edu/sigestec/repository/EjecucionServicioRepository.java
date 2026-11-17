package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.EjecucionServicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EjecucionServicioRepository extends JpaRepository<EjecucionServicio, Long> {
    boolean existsByOrdenTrabajoId(Long id);
    java.util.Optional<pe.edu.sigestec.entity.EjecucionServicio> findByOrdenTrabajoId(Long id);
}
