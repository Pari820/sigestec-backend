package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.DocumentoPlanificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoPlanificacionRepository extends JpaRepository<DocumentoPlanificacion, Long> {
    java.util.List<DocumentoPlanificacion> findByOrdenTrabajoId(Long id);

    java.util.Optional<DocumentoPlanificacion> findByOrdenTrabajoIdAndTipoDocumento(Long id, String tipo);
}
