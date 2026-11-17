package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.MovimientoMaterialAlmacen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoMaterialAlmacenRepository extends JpaRepository<MovimientoMaterialAlmacen, Long> {
    java.util.List<pe.edu.sigestec.entity.MovimientoMaterialAlmacen> findByOrdenTrabajoIdAndMaterialId(Long ordenTrabajoId,Long materialId);
}
