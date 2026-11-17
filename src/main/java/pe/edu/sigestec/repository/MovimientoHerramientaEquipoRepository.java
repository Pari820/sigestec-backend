package pe.edu.sigestec.repository;

import pe.edu.sigestec.entity.MovimientoHerramientaEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoHerramientaEquipoRepository extends JpaRepository<MovimientoHerramientaEquipo, Long> {
    java.util.List<pe.edu.sigestec.entity.MovimientoHerramientaEquipo> findByOrdenTrabajoIdAndHerramientaEquipoIdAndEmpleadoResponsableId(Long ot,Long herramienta,Long empleado);
}
