package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.AprobacionAts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AprobacionAtsRepository extends JpaRepository<AprobacionAts, Long> {
    boolean existsByAnalisisTrabajoSeguroId(Long id);
}
