package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.AsignacionPersonal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignacionPersonalRepository extends JpaRepository<AsignacionPersonal, Long> {
    java.util.List<AsignacionPersonal> findByOrdenTrabajoIdAndEstado(Long id, String estado);

    java.util.List<AsignacionPersonal> findByOrdenTrabajoId(Long id);

    void deleteByOrdenTrabajoId(Long id);
}
