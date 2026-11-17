package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.AnalisisTrabajoSeguro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalisisTrabajoSeguroRepository extends JpaRepository<AnalisisTrabajoSeguro, Long> {
    boolean existsByOrdenTrabajoId(Long id);
}
