package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
    java.util.List<Cotizacion> findBySolicitudServicioId(Long id);

    boolean existsByNumeroCotizacion(String numeroCotizacion);
}
