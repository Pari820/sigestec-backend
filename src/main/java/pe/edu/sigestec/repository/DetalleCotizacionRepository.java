package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.DetalleCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, Long> {
    java.util.List<DetalleCotizacion> findByCotizacionIdOrderByItemAsc(Long id);

    void deleteByCotizacionId(Long id);
}
