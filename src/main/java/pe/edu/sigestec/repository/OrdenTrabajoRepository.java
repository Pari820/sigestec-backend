package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.OrdenTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo, Long> {
    boolean existsByOrdenCompraId(Long id);

    java.util.Optional<OrdenTrabajo> findByOrdenCompraId(Long id);
}
