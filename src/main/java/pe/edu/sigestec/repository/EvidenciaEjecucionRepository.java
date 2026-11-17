package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.EvidenciaEjecucion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidenciaEjecucionRepository extends JpaRepository<EvidenciaEjecucion, Long> {
    java.util.List<pe.edu.sigestec.entity.EvidenciaEjecucion> findByEjecucionServicioId(Long id);
}
