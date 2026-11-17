package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.DetalleOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Long> {
    java.util.List<DetalleOrdenCompra> findByOrdenCompraIdOrderByItemAsc(Long id);
}
