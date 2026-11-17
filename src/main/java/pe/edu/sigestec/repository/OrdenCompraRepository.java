package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    boolean existsByCotizacionId(Long id);

    java.util.Optional<OrdenCompra> findByCotizacionId(Long id);

    boolean existsByNumeroOrdenCompraIgnoreCase(String numero);
}
