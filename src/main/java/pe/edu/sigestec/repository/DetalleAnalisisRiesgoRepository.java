package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.DetalleAnalisisRiesgo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleAnalisisRiesgoRepository extends JpaRepository<DetalleAnalisisRiesgo, Long> {
    java.util.List<DetalleAnalisisRiesgo> findByAnalisisTrabajoSeguroId(Long id);
}
